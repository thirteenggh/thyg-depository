package org.sonatype.nexus.repository.npm.internal.orient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityHelper;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.npm.internal.NpmAttributes;
import org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils;
import org.sonatype.nexus.repository.npm.internal.NpmPackageId;
import org.sonatype.nexus.repository.npm.internal.NpmPublishRequest;
import org.sonatype.nexus.repository.npm.orient.NpmFacet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalDeleteBlob;
import org.sonatype.nexus.repository.transaction.TransactionalStoreBlob;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.repository.transaction.TransactionalTouchBlob;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.transaction.UnitOfWork;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.io.IOUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Arrays.asList;
import static org.sonatype.nexus.repository.npm.internal.NpmFieldFactory.missingRevFieldMatcher;
import static org.sonatype.nexus.repository.npm.internal.NpmFieldFactory.rewriteTarballUrlMatcher;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.DIST_TAGS;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.META_ID;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.META_REV;
import static org.sonatype.nexus.repository.npm.internal.NpmMetadataUtils.selectVersionByTarballName;
import static org.sonatype.nexus.repository.npm.internal.NpmVersionComparator.extractAlwaysPackageVersion;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmFacetUtils.findPackageRootAsset;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmFacetUtils.findPackageTarballComponent;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmFacetUtils.savePackageRoot;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmFacetUtils.toContent;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmPackageRootMetadataUtils.createFullPackageMetadata;

/**
 * {@link OrientNpmHostedFacet} implementation.
 *
 * @since 3.0
 */
@Named
public class OrientNpmHostedFacetImpl
    extends FacetSupport
    implements OrientNpmHostedFacet
{
  private final NpmRequestParser npmRequestParser;

  @Inject
  public OrientNpmHostedFacetImpl(final NpmRequestParser npmRequestParser) {
    this.npmRequestParser = checkNotNull(npmRequestParser);
  }

  @Override
  protected void doInit(final Configuration configuration) throws Exception {
    super.doInit(configuration);
    getRepository().facet(StorageFacet.class).registerWritePolicySelector(new NpmWritePolicySelector());
  }

  @Override
  @TransactionalTouchBlob
  public Optional<Content> getPackage(final NpmPackageId packageId) throws IOException {
    checkNotNull(packageId);
    log.debug("Getting package: {}", packageId);
    StorageTx tx = UnitOfWork.currentTx();
    Asset packageRootAsset = findPackageRootAsset(tx, tx.findBucket(getRepository()), packageId);
    if (packageRootAsset == null) {
      return Optional.empty();
    }

    return Optional.of(toContent(getRepository(), packageRootAsset)
        .fieldMatchers(asList(
            missingRevFieldMatcher(() -> generateNewRevId(packageRootAsset)),
            rewriteTarballUrlMatcher(getRepository().getName(), packageId.id())))
        .packageId(packageRootAsset.name()));
  }

  protected String generateNewRevId(final Asset packageRootAsset) {
    String newRevision = EntityHelper.version(packageRootAsset).getValue();

    // For NEXUS-18094 we gonna request to upgrade the actual asset
    getEventManager().post(new NpmRevisionUpgradeRequestEvent(packageRootAsset, newRevision));

    return newRevision;
  }

  /**
   * For NEXUS-18094 we moved the revision number to live in the package root file so that the revision number doesn't
   * change as the database record changes (previously it used the Orient Document Version number). This method allows
   * us to avoid the need for an upgrade step by upgrading package roots without a rev as they are fetched.
   */
  @Subscribe
  public void on(final NpmRevisionUpgradeRequestEvent event) {
    UnitOfWork.begin(getRepository().facet(StorageFacet.class).txSupplier());
    try {
      upgradeRevisionOnPackageRoot(event.getPackageRootAsset(), event.getRevision());
    }
    finally {
      UnitOfWork.end();
    }
  }

  @TransactionalTouchBlob
  protected void upgradeRevisionOnPackageRoot(final Asset packageRootAsset, final String revision) {
    StorageTx tx = UnitOfWork.currentTx();

    NpmPackageId packageId = NpmPackageId.parse(packageRootAsset.name());
    Asset asset = findPackageRootAsset(tx, tx.findBucket(getRepository()), packageId);

    if (asset == null) {
      log.error("Failed to update revision on package root. Asset for id '{}' didn't exist", packageId.id());
      return;
    }

    // if there is a transaction failure and we fail to upgrade the package root with _rev
    // then the user who fetched the package root will not be able to run a delete command
    try {
      NestedAttributesMap packageRoot = NpmFacetUtils.loadPackageRoot(tx, asset);
      packageRoot.set(META_REV, revision);
      savePackageRoot(UnitOfWork.currentTx(), packageRootAsset, packageRoot);
    }
    catch (IOException e) {
      log.warn("Failed to update revision in package root. Revision '{}' was not set" +
              " and might cause delete for that revision to fail for Asset {}",
          revision, packageRootAsset, e);
    }
  }

  @Override
  public void putPackage(final NpmPackageId packageId, @Nullable final String revision, final Payload payload)
      throws IOException
  {
    checkNotNull(packageId);
    checkNotNull(payload);
    try (NpmPublishRequest request = npmRequestParser.parsePublish(getRepository(), payload)) {
      putPublishRequest(packageId, revision, request);
    }
  }

  @Override
  public Asset putPackage(final Map<String, Object> packageJson, final TempBlob tempBlob) throws IOException {
    checkNotNull(packageJson);
    checkNotNull(tempBlob);

    log.debug("Storing package: {}", packageJson);

    checkNotNull(packageJson.get(NpmAttributes.P_NAME), "Uploaded package is invalid, or is missing package.json");

    NestedAttributesMap metadata = createFullPackageMetadata(
        new NestedAttributesMap("metadata", packageJson),
        getRepository().getName(),
        tempBlob.getHashes().get(HashAlgorithm.SHA1).toString(),
        null,
        extractAlwaysPackageVersion);

    NpmPackageId packageId = NpmPackageId.parse((String) metadata.get(NpmAttributes.P_NAME));

    return putPackage(packageId, metadata, tempBlob);
  }

  @TransactionalStoreBlob
  protected Asset putPackage(final NpmPackageId packageId,
                             final NestedAttributesMap requestPackageRoot,
                             final TempBlob tarballTempBlob)
      throws IOException
  {
    checkNotNull(packageId);
    checkNotNull(requestPackageRoot);
    checkNotNull(tarballTempBlob);

    log.debug("Storing package: {}", packageId);

    StorageTx tx = UnitOfWork.currentTx();

    String tarballName = NpmMetadataUtils.extractTarballName(requestPackageRoot);
    AssetBlob assetBlob = NpmFacetUtils.createTarballAssetBlob(tx, packageId, tarballName, tarballTempBlob);

    NpmFacet npmFacet = facet(NpmFacet.class);
    Asset asset = npmFacet.putTarball(packageId.id(), tarballName, assetBlob, new AttributesMap());

    putPackageRoot(packageId, null, requestPackageRoot);

    return asset;
  }

  @TransactionalStoreBlob
  protected void putPublishRequest(final NpmPackageId packageId,
                                   @Nullable final String revision,
                                   final NpmPublishRequest request)
      throws IOException
  {
    log.debug("Storing package: {}", packageId);
    StorageTx tx = UnitOfWork.currentTx();

    NestedAttributesMap packageRoot = request.getPackageRoot();

    // process attachments, if any
    NestedAttributesMap attachments = packageRoot.child("_attachments");
    if (!attachments.isEmpty()) {
      for (String name : attachments.keys()) {
        NestedAttributesMap attachment = attachments.child(name);
        NestedAttributesMap packageVersion = selectVersionByTarballName(packageRoot, name);
        putTarball(tx, packageId, packageVersion, attachment, request);
      }
    }

    putPackageRoot(packageId, revision, packageRoot);
  }

  /**
   * Note: transactional method cannot be private, must be protected (as CGLIB will extend it).
   */
  @Override
  @TransactionalStoreBlob
  public void putPackageRoot(final NpmPackageId packageId,
                             @Nullable final String revision,
                             final NestedAttributesMap newPackageRoot)
      throws IOException
  {
    log.debug("Storing package root: {}", packageId);
    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());
    boolean update = false;

    NestedAttributesMap packageRoot = newPackageRoot;
    Asset packageRootAsset = findPackageRootAsset(tx, bucket, packageId);
    if (packageRootAsset != null) {
      NestedAttributesMap oldPackageRoot = NpmFacetUtils.loadPackageRoot(tx, packageRootAsset);

      String rev = revision;
      if (rev == null) {
        rev = packageRoot.get(META_REV, String.class);
      }
      // ensure revision is expected, client updates package that is in expected state
      if (rev != null) {
        // if revision is present, full document is being sent, no overlay must occur
        checkArgument(rev.equals(oldPackageRoot.get(META_REV, String.class)));
        update = true;
      }
      else {
        // if no revision present, snippet is being sent, overlay it (if old exists)
        packageRoot = NpmMetadataUtils.overlay(oldPackageRoot, packageRoot);
      }
    }

    boolean createdPackageRoot = false;
    if (packageRootAsset == null) {
      packageRootAsset = tx.createAsset(bucket, getRepository().getFormat()).name(packageId.id());
      createdPackageRoot = true;
    }

    updateRevision(packageRoot, packageRootAsset, createdPackageRoot);

    savePackageRoot(tx, packageRootAsset, packageRoot);
    if (update) {
      updateDeprecationFlags(tx, packageId, packageRoot);
    }
  }

  private void updateRevision(final NestedAttributesMap packageRoot,
                              final Asset packageRootAsset,
                              final boolean createdPackageRoot)
  {
    String newRevision = "1";

    if (!createdPackageRoot) {
      if (packageRoot.contains(META_REV)) {
        String rev = packageRoot.get(META_REV, String.class);
        newRevision = Integer.toString(Integer.parseInt(rev) + 1);
      }
      else {
        /*
          This is covering the edge case when a new package is uploaded to a repository where the packageRoot already
          exists.

          If that packageRoot was created using an earlier version of NXRM where we didn't store the rev then we need
          to add it in. We also add the rev in on download but it is possible that someone is uploading a package where
          the packageRoot has never been downloaded before.
         */
        newRevision = EntityHelper.version(packageRootAsset).getValue();
      }
    }

    packageRoot.set(META_ID, packageRootAsset.name());
    packageRoot.set(META_REV, newRevision);
  }

  /**
   * Updates all the tarball components that belong to given package, updating their deprecated flags. Only changed
   * {@link Component}s are modified and saved.
   */
  private void updateDeprecationFlags(final StorageTx tx,
                                      final NpmPackageId packageId,
                                      final NestedAttributesMap packageRoot)
  {
    final NestedAttributesMap versions = packageRoot.child(NpmMetadataUtils.VERSIONS);
    for (Component tarballComponent : NpmFacetUtils.findPackageTarballComponents(tx, getRepository(), packageId)) {
      // integrity check: package doc must contain the tarball version
      checkState(versions.contains(tarballComponent.version()), "Package %s lacks tarball version %s", packageId,
          tarballComponent.version());
      final NestedAttributesMap version = versions.child(tarballComponent.version());
      final String deprecationMessage = version.get(NpmMetadataUtils.DEPRECATED, String.class);
      // in npm JSON, deprecated with non-empty string means deprecated, with empty or not present is not deprecated
      final boolean deprecated = !Strings2.isBlank(deprecationMessage);
      if (deprecated && !deprecationMessage
          .equals(tarballComponent.formatAttributes().get(NpmAttributes.P_DEPRECATED, String.class))) {
        tarballComponent.formatAttributes().set(NpmAttributes.P_DEPRECATED, deprecationMessage);
        tx.saveComponent(tarballComponent);
      }
      else if (!deprecated && tarballComponent.formatAttributes().contains(NpmAttributes.P_DEPRECATED)) {
        tarballComponent.formatAttributes().remove(NpmAttributes.P_DEPRECATED);
        tx.saveComponent(tarballComponent);
      }
    }
  }

  private void putTarball(final StorageTx tx,
                          final NpmPackageId packageId,
                          final NestedAttributesMap packageVersion,
                          final NestedAttributesMap attachment,
                          final NpmPublishRequest request) throws IOException
  {
    String tarballName = NpmMetadataUtils.extractTarballName(attachment.getKey());
    log.debug("Storing tarball: {}@{} ({})",
        packageId,
        packageVersion.get(NpmMetadataUtils.VERSION, String.class),
        tarballName);

    TempBlob tempBlob = (TempBlob) request.requireBlob(attachment.require("data", String.class));
    AssetBlob assetBlob = NpmFacetUtils.createTarballAssetBlob(tx, packageId, tarballName, tempBlob);

    NpmFacet npmFacet = facet(NpmFacet.class);
    npmFacet.putTarball(packageId.id(), tarballName, assetBlob, new AttributesMap());
  }

  @Override
  @TransactionalDeleteBlob
  public Set<String> deletePackage(final NpmPackageId packageId, @Nullable final String revision) throws IOException {
    return deletePackage(packageId, revision, true);
  }

  @Override
  @TransactionalDeleteBlob
  public Set<String> deletePackage(final NpmPackageId packageId,
                                   @Nullable final String revision,
                                   final boolean deleteBlobs)
      throws IOException
  {
    checkNotNull(packageId);
    StorageTx tx = UnitOfWork.currentTx();
    if (revision != null) {
      Asset packageRootAsset = findPackageRootAsset(tx, tx.findBucket(getRepository()), packageId);
      if (packageRootAsset != null) {
        NestedAttributesMap oldPackageRoot = NpmFacetUtils.loadPackageRoot(tx, packageRootAsset);
        checkArgument(revision.equals(oldPackageRoot.get(META_REV, String.class)));
      }
    }

    return NpmFacetUtils.deletePackageRoot(tx, getRepository(), packageId, deleteBlobs);
  }

  @Override
  @TransactionalTouchBlob
  public Optional<Content> getTarball(final NpmPackageId packageId, final String tarballName) throws IOException {
    checkNotNull(packageId);
    checkNotNull(tarballName);
    StorageTx tx = UnitOfWork.currentTx();
    return NpmFacetUtils.getTarballContent(tx, tx.findBucket(getRepository()), packageId, tarballName);
  }

  @Override
  @TransactionalDeleteBlob
  public Optional<String> deleteTarball(final NpmPackageId packageId, final String tarballName) {
    return deleteTarball(packageId, tarballName, true);
  }

  @Override
  @TransactionalDeleteBlob
  public Optional<String> deleteTarball(final NpmPackageId packageId, final String tarballName, final boolean deleteBlob) {
    checkNotNull(packageId);
    checkNotNull(tarballName);
    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());

    Asset tarballAsset = NpmFacetUtils.findTarballAsset(tx, bucket, packageId, tarballName);
    if (tarballAsset == null) {
      return Optional.empty();
    }
    Component tarballComponent = tx.findComponentInBucket(tarballAsset.componentId(), bucket);
    if (tarballComponent == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(Iterables.getOnlyElement(tx.deleteComponent(tarballComponent, deleteBlob), null));
  }

  @Override
  @TransactionalTouchBlob
  public Optional<Content> getDistTags(final NpmPackageId packageId) {
    checkNotNull(packageId);
    log.debug("Getting package: {}", packageId);
    StorageTx tx = UnitOfWork.currentTx();
    Asset packageRootAsset = findPackageRootAsset(tx, tx.findBucket(getRepository()), packageId);
    if (packageRootAsset == null) {
      return Optional.empty();
    }

    try {
      final NestedAttributesMap packageRoot = NpmFacetUtils.loadPackageRoot(tx, packageRootAsset);
      final NestedAttributesMap distTags = packageRoot.child(DIST_TAGS);
      return Optional.of(NpmFacetUtils.distTagsToContent(distTags));
    }
    catch (IOException e) {
      log.info("Unable to obtain dist-tags for {}", packageId.id(), e);
      return Optional.empty();
    }
  }

  @Override
  public void putDistTags(final NpmPackageId packageId, final String tag, final Payload payload)
      throws IOException
  {
    checkNotNull(packageId);
    checkNotNull(tag);
    log.debug("Updating distTags: {}", packageId);

    if ("latest".equals(tag)) {
      throw new IOException("Unable to update latest tag");
    }

    String version = parseVersionToTag(packageId, tag, payload);
    doPutDistTags(packageId, tag, version);
  }

  @TransactionalStoreMetadata
  protected void doPutDistTags(final NpmPackageId packageId, final String tag, final String version)
      throws IOException
  {
    StorageTx tx = UnitOfWork.currentTx();
    Asset packageRootAsset = findPackageRootAsset(tx, tx.findBucket(getRepository()), packageId);
    if (packageRootAsset == null) {
      return;
    }

    if (findPackageTarballComponent(tx, getRepository(), packageId, version) == null) {
      throw new IOException(String
          .format("version %s of package %s is not present in repository %s", version, packageId.id(),
              getRepository().getName()));
    }

    try {
      NpmFacetUtils.updateDistTags(tx, packageRootAsset, tag, version);
    }
    catch (IOException e) {
      log.error("Unable to update dist-tags for {}", packageId.id(), e);
    }
  }

  @TransactionalStoreMetadata
  @Override
  public void deleteDistTags(final NpmPackageId packageId, final String tag, final Payload payload) throws IOException
  {
    checkNotNull(packageId);
    checkNotNull(tag);
    log.debug("Deleting distTags: {}", packageId);

    if ("latest".equals(tag)) {
      throw new IOException("Unable to delete latest");
    }

    StorageTx tx = UnitOfWork.currentTx();
    Asset packageRootAsset = findPackageRootAsset(tx, tx.findBucket(getRepository()), packageId);
    if (packageRootAsset == null) {
      return;
    }

    try {
      NpmFacetUtils.deleteDistTags(tx, packageRootAsset, tag);
    }
    catch (IOException e) {
      log.info("Unable to obtain dist-tags for {}", packageId.id(), e);
    }
  }

  private String parseVersionToTag(final NpmPackageId packageId,
                                   @Nullable final String tag,
                                   final Payload payload) throws IOException
  {
    String version;
    try (InputStream is = payload.openInputStream()) {
      version = IOUtils.toString(is).replaceAll("\"", "");
      log.debug("Adding tag {}:{} to {}", tag, version, packageId);
    }
    return version;
  }
}
