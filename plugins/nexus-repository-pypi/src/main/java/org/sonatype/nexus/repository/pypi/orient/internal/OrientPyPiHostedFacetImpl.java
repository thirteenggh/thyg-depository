package org.sonatype.nexus.repository.pypi.orient.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.common.template.TemplateHelper;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.pypi.internal.AssetKind;
import org.sonatype.nexus.repository.pypi.internal.PyPiAttributes;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;
import org.sonatype.nexus.repository.pypi.internal.PyPiIndexFacet;
import org.sonatype.nexus.repository.pypi.internal.PyPiInfoUtils;
import org.sonatype.nexus.repository.pypi.internal.PyPiLink;
import org.sonatype.nexus.repository.pypi.internal.SignablePyPiPackage;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalStoreBlob;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.repository.transaction.TransactionalTouchBlob;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.payloads.StringPayload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.repository.view.payloads.TempBlobPartPayload;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.UnitOfWork;

import org.apache.commons.lang.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.sonatype.nexus.common.hash.HashAlgorithm.MD5;
import static org.sonatype.nexus.repository.pypi.internal.AssetKind.ROOT_INDEX;
import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_NAME;
import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_SUMMARY;
import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_VERSION;
import static org.sonatype.nexus.repository.pypi.internal.PyPiIndexUtils.buildIndexPage;
import static org.sonatype.nexus.repository.pypi.internal.PyPiIndexUtils.buildRootIndexPage;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.INDEX_PATH_PREFIX;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.indexPath;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.normalizeName;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.packagesPath;
import static org.sonatype.nexus.repository.pypi.internal.PyPiStorageUtils.HASH_ALGORITHMS;
import static org.sonatype.nexus.repository.pypi.internal.PyPiStorageUtils.getNameAttributes;
import static org.sonatype.nexus.repository.pypi.internal.PyPiStorageUtils.mayAddEtag;
import static org.sonatype.nexus.repository.pypi.internal.PyPiStorageUtils.validateMd5Hash;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.copyAttributes;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.findAsset;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.findAssetsByComponentName;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.findComponent;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.findComponentExists;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.saveAsset;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.toContent;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;
import static org.sonatype.nexus.repository.view.ContentTypes.TEXT_HTML;

/**
 * {@link OrientPyPiHostedFacet} implementation.
 *
 * @since 3.1
 */
@Named
public class OrientPyPiHostedFacetImpl
    extends FacetSupport
    implements OrientPyPiHostedFacet
{
  private final TemplateHelper templateHelper;

  @Inject
  public OrientPyPiHostedFacetImpl(final TemplateHelper templateHelper) {
    this.templateHelper = checkNotNull(templateHelper);
  }

  @Override
  @TransactionalStoreBlob
  @Nullable
  public Content getRootIndex() {
    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());

    Asset asset = findAsset(tx, bucket, INDEX_PATH_PREFIX);
    if (asset == null) {
      try {
        return createAndSaveRootIndex(bucket);
      }
      catch (IOException e) {
        log.error("Unable to create root index for repository: {}", getRepository().getName(), e);
        return null;
      }
    }

    return toContent(asset, tx.requireBlob(asset.requireBlobRef()));
  }

  private Content createAndSaveRootIndex(final Bucket bucket) throws IOException {
    StorageTx tx = UnitOfWork.currentTx();
    List<PyPiLink> links = findAllLinks();

    Asset asset = createRootIndexAsset(bucket);

    String rootIndexHtml = buildRootIndexPage(templateHelper, links);
    return storeHtmlPage(tx, asset, rootIndexHtml);
  }

  @TransactionalStoreMetadata
  protected Asset createRootIndexAsset(final Bucket bucket) {
    StorageTx tx = UnitOfWork.currentTx();
    Asset asset = tx.createAsset(bucket, getRepository().getFormat());
    asset.name(INDEX_PATH_PREFIX);
    asset.formatAttributes().set(P_ASSET_KIND, ROOT_INDEX.name());
    return asset;
  }

  @Transactional
  protected List<PyPiLink> findAllLinks() {
    StorageTx tx = UnitOfWork.currentTx();
    Map<String, PyPiLink> links = new TreeMap<>();
    Iterable<Component> components = tx.browseComponents(tx.findBucket(getRepository()));
    components.forEach(c -> links.put(c.name(), new PyPiLink(c.name(), c.name() + "/")));
    return new ArrayList<>(links.values());
  }

  @TransactionalStoreBlob
  protected Content storeHtmlPage(final StorageTx tx, final Asset asset, final String indexPage) throws IOException
  {
    StorageFacet storageFacet = facet(StorageFacet.class);
    try (TempBlob tempBlob = storageFacet.createTempBlob(new StringPayload(indexPage, TEXT_HTML), HASH_ALGORITHMS)) {
      return saveAsset(tx, asset, tempBlob, TEXT_HTML, null);
    }
  }

  @Override
  @TransactionalStoreBlob
  public Content getIndex(final String name) throws IOException {
    checkNotNull(name);
    StorageTx tx = UnitOfWork.currentTx();

    // If we don't even have a single component entry, then nothing has been uploaded yet
    if (!findComponentExists(tx, getRepository(), name)) {
      return null;
    }

    String indexPath = indexPath(name);
    Bucket bucket = tx.findBucket(getRepository());
    Asset savedIndex = findAsset(tx, bucket, indexPath);

    if (savedIndex == null) {
      savedIndex = createIndexAsset(name, tx, indexPath, bucket);
    }

    return toContent(savedIndex, tx.requireBlob(savedIndex.requireBlobRef()));
  }

  private Asset createIndexAsset(final String name,
                                 final StorageTx tx,
                                 final String indexPath,
                                 final Bucket bucket) throws IOException
  {
    String html = buildIndex(name, tx);

    Asset savedIndex = tx.createAsset(bucket, getRepository().getFormat());
    savedIndex.name(indexPath);
    savedIndex.formatAttributes().set(P_ASSET_KIND, AssetKind.INDEX.name());

    StorageFacet storageFacet = getRepository().facet(StorageFacet.class);

    try (TempBlob tempBlob = storageFacet.createTempBlob(new ByteArrayInputStream(html.getBytes(UTF_8)), HASH_ALGORITHMS)) {
      saveAsset(tx, savedIndex, tempBlob, TEXT_HTML, new AttributesMap());
    }

    return savedIndex;
  }

  private String buildIndex(final String name, final StorageTx tx) {
    List<PyPiLink> links = new ArrayList<>();
    for (Asset asset : findAssetsByComponentName(tx, getRepository(), name)) {
      AttributesMap pypiAttributes = asset.attributes().child(PyPiFormat.NAME);
      String path = asset.name();
      String file = path.substring(path.lastIndexOf('/') + 1);
      String link = String.format("../../%s#md5=%s", path, asset.getChecksum(MD5));
      String dataRequiresPython = pypiAttributes.get(PyPiAttributes.P_REQUIRES_PYTHON, String.class, StringUtils.EMPTY);
      links.add(new PyPiLink(file, link, dataRequiresPython));
    }

    return buildIndexPage(templateHelper, name, links);
  }

  @Override
  @TransactionalTouchBlob
  public Content getPackage(final String packagePath) {
    checkNotNull(packagePath);
    StorageTx tx = UnitOfWork.currentTx();

    Asset asset = findAsset(tx, tx.findBucket(getRepository()), packagePath);
    if (asset == null) {
      return null;
    }
    Content content = toContent(asset, tx.requireBlob(asset.requireBlobRef()));
    mayAddEtag(content.getAttributes(), String.valueOf(asset.getChecksum(HashAlgorithm.SHA1)));
    return content;
  }

  @TransactionalStoreBlob
  @Override
  public Asset upload(final String filename, final Map<String, String> attributes, final TempBlobPartPayload payload)
      throws IOException
  {
    return savePyPiWheelPayload(filename, attributes, payload);
  }

  @TransactionalStoreBlob
  @Override
  public Content uploadSignature(
      final String name,
      final String version,
      final TempBlobPartPayload payload)
  {
    return storeGpgSignaturePayload(payload, name, version);
  }

  protected Asset savePyPiWheelPayload(
      final String filename,
      final Map<String, String> attributes,
      final TempBlobPartPayload wheelPayload) throws IOException
  {
    checkNotNull(filename);

    TempBlob tempBlob = wheelPayload.getTempBlob();

    final String name = checkNotNull(attributes.get(P_NAME));
    final String version = checkNotNull(attributes.get(P_VERSION));
    final String normalizedName = normalizeName(name);

    validateMd5Hash(attributes, tempBlob);

    PyPiIndexFacet indexFacet = facet(PyPiIndexFacet.class);
    // A package has been added or redeployed and therefore the cached index is no longer relevant
    indexFacet.deleteIndex(name);

    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());

    String packagePath = createPackagePath(name, version, filename);

    Component component = findOrCreateComponent(name, version, normalizedName, indexFacet, tx, bucket);

    component.formatAttributes().set(P_SUMMARY, attributes.get(P_SUMMARY)); // use the most recent summary received?
    tx.saveComponent(component);

    Asset asset = findOrCreateAsset(tx, bucket, packagePath, component, AssetKind.PACKAGE.name());

    copyAttributes(asset, attributes);
    saveAsset(tx, asset, tempBlob, wheelPayload);

    return asset;
  }

  private Component findOrCreateComponent(
      final String name,
      final String version,
      final String normalizedName,
      final PyPiIndexFacet indexFacet, final StorageTx tx, final Bucket bucket)
  {
    Component component = findComponent(tx, getRepository(), normalizedName, version);
    if (component == null) {
      component = createComponent(name, version, normalizedName, tx, bucket);

      //A new component so we will need to regenerate the root index
      indexFacet.deleteRootIndex();
    }
    return component;
  }

  private Component createComponent(
      final String name,
      final String version,
      final String normalizedName,
      final StorageTx tx, final Bucket bucket)
  {
    Component component;//
    // The setup.py register and setup.py upload operations in Python distutils do not correctly generate CR/LF
    // sequences, which causes commons-fileupload to be unable to read the headers and throws an exception. This is
    // a known issue to the Python maintainers and a fix was made for newer distutils releases, but even though both
    // affected code paths are mentioned in the issue, they only remembered to fix the upload operation.
    //
    // Allowing creation of a new component on upload seems to be the most obvious way we can implement a workaround
    // on our side, even though it is not directly in keeping with how the tools are supposed to work.
    //
    // See https://bugs.python.org/issue10510 for the "fixed" Python distutils issue.
    //
    component = tx.createComponent(bucket, getRepository().getFormat()).name(normalizedName).version(version);
    getNameAttributes(name).forEach((k,v) -> component.formatAttributes().set(k, v));
    component.formatAttributes().set(P_VERSION, version);
    return component;
  }

  private Asset findOrCreateAsset(
      final StorageTx tx,
      final Bucket bucket,
      final String packagePath,
      final Component component, final String assetKind)
  {
    Asset asset = findAsset(tx, bucket, packagePath);
    if (asset == null) {
      asset = tx.createAsset(bucket, component);
      asset.name(packagePath);
      asset.formatAttributes().set(P_ASSET_KIND, assetKind);
    }
    return asset;
  }

  @Override
  public Asset upload(final SignablePyPiPackage pyPiPackage) throws IOException {
    Asset savedPiPyPackage;
    Map<String, String> attributes = pyPiPackage.getAttributes();
    try (
        TempBlobPartPayload wheelPayload = pyPiPackage.getWheelPayload();
        TempBlobPartPayload gpgPayload = pyPiPackage.getGpgSignature()) {
      savedPiPyPackage = storeWheelAndSignaturePayloads(wheelPayload, gpgPayload, attributes);
    }
    catch (Exception e) {
      log.info("Unable to store wheel and gpg signature", e);
      throw e;
    }
    return savedPiPyPackage;
  }

  @TransactionalStoreBlob
  protected Asset storeWheelAndSignaturePayloads(final TempBlobPartPayload wheelPayload,
                                               final TempBlobPartPayload gpgPayload,
                                               final Map<String, String> attributes) throws IOException
  {
    Asset wheelAsset = storeWheelPayload(wheelPayload, attributes);
    storeGpgSignaturePayload(gpgPayload, attributes);
    return wheelAsset;
  }

  protected Asset storeWheelPayload(final TempBlobPartPayload wheelPayload,
                                    final Map<String, String> attributes) throws IOException
  {
    String wheelFileName = wheelPayload.getName();
    return savePyPiWheelPayload(wheelFileName, attributes, wheelPayload);
  }

  protected Content storeGpgSignaturePayload(
      final TempBlobPartPayload gpgPayload,
      final Map<String, String> attributes)
  {
    return storeGpgSignaturePayload(gpgPayload, attributes.get(P_NAME), attributes.get(P_VERSION));
  }

  protected Content storeGpgSignaturePayload(final TempBlobPartPayload gpgPayload,
                                             final String name,
                                             final String version)
  {
    if (gpgPayload != null) {
      StorageTx tx = UnitOfWork.currentTx();

      Component component = findOrCreateComponent(name, version, normalizeName(name), facet(PyPiIndexFacet.class), tx,
          tx.findBucket(getRepository()));

      if (component.isNew()) {
        tx.saveComponent(component);
      }

      Asset asset = createGpgSignatureAsset(gpgPayload.getName(), name, version, component, tx);

      return saveGpgSignatureAsset(tx, asset, gpgPayload);
    }
    return null;
  }

  private Asset createGpgSignatureAsset(
      final String signatureFileName,
      final String name,
      final String version, final Component component,
      final StorageTx tx)
  {
    String packageSignaturePath = createPackagePath(name, version, signatureFileName);
    Bucket bucket = tx.findBucket(getRepository());
    return findOrCreateAsset(tx, bucket, packageSignaturePath, component, AssetKind.PACKAGE_SIGNATURE.name());
  }

  private Content saveGpgSignatureAsset(
      final StorageTx tx,
      final Asset asset,
      final TempBlobPartPayload gpgSignaturePayload)
  {
    try {
      return saveAsset(tx, asset, gpgSignaturePayload.getTempBlob(), gpgSignaturePayload);
    }
    catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public String createPackagePath(final String name, final String version, final String filename) {
    final String normalizedName = normalizeName(name);
    return packagesPath(normalizedName, version, filename);
  }

  @Override
  public Map<String, String> extractMetadata(final TempBlob tempBlob) throws IOException {
    try (InputStream in = tempBlob.get()) {
      return PyPiInfoUtils.extractMetadata(in);
    }
  }
}
