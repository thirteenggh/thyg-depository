package org.sonatype.nexus.testsuite.helpers;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityHelper;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.attributes.AttributesFacet;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentMaintenance;
import org.sonatype.nexus.repository.storage.Query;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;

import com.google.common.collect.ImmutableList;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import org.joda.time.DateTime;

import static com.google.common.collect.Lists.newArrayList;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.stripStart;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;

@FeatureFlag(name = "nexus.orient.store.content")
@Named
@Singleton
@Priority(Integer.MAX_VALUE)
public class OrientComponentAssetTestHelper
    implements ComponentAssetTestHelper
{
  private static final String DELETE_COMPONENT_SQL =
      "delete from component where group = ? and name = ? and version = ?";

  private static final DateTimeFormatter YEAR_MONTH_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

  private static final String SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT";

  @Inject
  @Named(DatabaseInstanceNames.COMPONENT)
  public Provider<DatabaseInstance> databaseInstanceProvider;

  @Inject
  RepositoryManager repositoryManager;

  @Override
  public DateTime getBlobCreatedTime(final Repository repository, final String path) {
    return findAssetByName(repository, path).map(Asset::blobCreated).orElse(null);
  }

  @Override
  public DateTime getAssetCreatedTime(final Repository repository, final String path) {
    //not sure why it is the same
    return getBlobCreatedTime(repository, path);
  }

  @Override
  public DateTime getBlobUpdatedTime(final Repository repository, final String path) {
    return findAssetByNameNoBucketFind(repository, path).map(Asset::blobUpdated).orElse(null);
  }

  @Override
  public DateTime getLastDownloadedTime(final Repository repository, final String path) {
    return findAssetByName(repository, path).map(Asset::lastDownloaded).orElse(null);
  }

  @Override
  public void removeAsset(final Repository repository, final String path) {
    EntityId nanoAssetId = EntityHelper.id(findAssetByNameNotNull(repository, path));
    ComponentMaintenance maintenanceFacet = repository.facet(ComponentMaintenance.class);
    maintenanceFacet.deleteAsset(nanoAssetId);
  }

  @Override
  public void deleteComponent(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();
      tx.getDb().command(new OCommandSQL(DELETE_COMPONENT_SQL)).execute(namespace, name, version);
      tx.commit();
    }
  }

  @Override
  public List<String> findAssetPaths(final String repositoryName) {
    String sql = "SELECT * FROM asset WHERE bucket.repository_name = ?";
    try (ODatabaseDocumentTx tx = databaseInstanceProvider.get().acquire()) {
      tx.begin();
      List<ODocument> results = tx.command(new OCommandSQL(sql)).execute(repositoryName);
      return results.stream().map(doc -> doc.field("name", String.class).toString()).collect(toList());
    }
  }

  @Override
  public String contentTypeFor(final String repositoryName, final String path) {
    return findAssetByNameNotNull(repositoryManager.get(repositoryName), path).contentType();
  }

  @Override
  public int countComponents(final Repository repository) {
    return findComponents(repository).size();
  }

  private List<Component> findComponents(final Repository repository) {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();
      return newArrayList(tx.browseComponents(tx.findBucket(repository)));
    }
  }

  private List<Asset> findAssets(final Repository repository) {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();
      return newArrayList(tx.browseAssets(tx.findBucket(repository)));
    }
  }

  private static Asset findAssetByNameNotNull(final Repository repository, final String name) {
    return findAssetByName(repository, name).orElseThrow(() -> new AssetNotFoundException(repository, name));
  }

  private static Optional<Asset> findAssetByName(final Repository repository, final String name) {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();
      return Optional.ofNullable(tx.findAssetWithProperty(P_NAME, name, tx.findBucket(repository)));
    }
  }

  private static Optional<Asset> findAssetByNameNoBucketFind(final Repository repository, final String name) {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();
      return Optional.ofNullable(tx.findAssetWithProperty(P_NAME, name));
    }
  }

  @Override
  public NestedAttributesMap attributes(final Repository repository, final String path) {
    return findAssetByNameNotNull(repository, path).attributes();
  }

  @Override
  public boolean assetExists(final Repository repository, final String path) {
    return findAssetByName(repository, path).isPresent();
  }

  @Override
  public boolean componentExists(final Repository repository, final String name) {
    return findComponents(repository).stream()
        .anyMatch(c -> name.equals(c.name()));
  }

  @Override
  public boolean componentExists(final Repository repository, final String name, final String version) {
    if (version.endsWith(SNAPSHOT_VERSION_SUFFIX)) {
      Optional<Component> component = findSnapshotComponent(repository, name, version);
      return component.isPresent();
    }
    else {
      return findComponents(repository).stream()
          .filter(c -> name.equals(c.name()))
          .anyMatch(c -> version.equals(c.version()));
    }
  }

  @Override
  public boolean componentExists(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    if (endsWith(version, SNAPSHOT_VERSION_SUFFIX)) {
      Optional<Component> component = findSnapshotComponent(repository, name, version);
      return component.isPresent();
    }
    else {
      return findComponent(repository, namespace, name, version).isPresent();
    }
  }

  @Override
  public boolean componentExistsWithAssetPathMatching(final Repository repository, final Predicate<String> pathMatcher) {
    return findAssets(repository).stream()
      .filter(asset -> Objects.nonNull(asset.componentId()))
      .map(Asset::name)
      .anyMatch(pathMatcher);
  }

  private Optional<Component> findSnapshotComponent(
      final Repository repository,
      final String name,
      final String version)
  {
    String gav = substring(version, 0, indexOf(version, SNAPSHOT_VERSION_SUFFIX));
    String versionWithDate = String.format("%s-%s", gav, now().format(YEAR_MONTH_DAY_FORMAT));
    return findComponents(repository)
        .stream()
        .filter(c -> name.equals(c.name()))
        .filter(comp -> startsWith(comp.version(), versionWithDate))
        .findAny();
  }

  @Override
  public boolean assetWithComponentExists(
      final Repository repository,
      final String path,
      final String group,
      final String name)
  {
    return findAssetByName(repository, path)
        .map(Asset::componentId)
        .flatMap(componentId -> {
          try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
            tx.begin();
            return Optional.ofNullable(tx.findComponent(componentId));
          }
        })
        .filter(component -> Objects.equals(component.group(), group))
        .filter(component -> Objects.equals(component.name(), name))
        .isPresent();
  }

  @Override
  public boolean assetWithComponentExists(
      final Repository repository,
      final String path,
      final String group,
      final String name,
      final String version)
  {
    return findAssetByName(repository, path)
        .map(Asset::componentId)
        .flatMap(componentId -> {
          try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
            tx.begin();
            return Optional.ofNullable(tx.findComponent(componentId));
          }
        })
        .filter(component -> group == null || Objects.equals(component.group(), group))
        .filter(component -> Objects.equals(component.name(), name))
        .filter(component -> Objects.equals(component.version(), version))
        .isPresent();
  }

  @Override
  public NestedAttributesMap componentAttributes(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    return findComponent(repository, namespace, name, version).map(Component::attributes)
        .orElseThrow(() -> new ComponentNotFoundException(repository, namespace, name, version));
  }

  @Override
  public NestedAttributesMap componentAttributes(
      final Repository repository,
      final String namespace,
      final String name)
  {
    return componentAttributes(repository, namespace, name, null);
  }

  private Optional<Component> findComponent(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    return findComponents(repository).stream()
        .filter(c -> Objects.equals(namespace, c.group()))
        .filter(c -> name.equals(c.name()))
        .filter(c -> version == null || version.equals(c.version()))
        .findAny();
  }

  @Override
  public boolean assetWithoutComponentExists(final Repository repository, final String path) {
    return !findAssetByName(repository, path).map(Asset::componentId).isPresent();
  }

  @Override
  public String adjustedPath(final String path) {
    return stripStart(path, "/");
  }

  @Override
  public void setLastDownloadedTime(final Repository repository, final int minusSeconds) {
    updateAssets(repository, asset -> asset.lastDownloaded(org.joda.time.DateTime.now().minusSeconds(minusSeconds)));
  }

  @Override
  public void setLastDownloadedTime(
      final Repository repository,
      final int minusSeconds,
      final Predicate<String> pathMatcher)
  {
    updateAssets(repository, asset -> {
      if (pathMatcher.test(asset.name())) {
        asset.lastDownloaded(org.joda.time.DateTime.now().minusSeconds(minusSeconds));
      }
    });
  }

  protected void updateAssets(final Repository repository, final Consumer<Asset> updater) {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();

      Iterable<Asset> assets = tx.browseAssets(tx.findBucket(repository));

      for (Asset asset : assets) {
        updater.accept(asset);
        tx.saveAsset(asset);
      }

      tx.commit();
    }
  }

  @Override
  public int countAssets(final Repository repository) {
    try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
      tx.begin();

      return (int) tx.countAssets(Query.builder().where("1").eq("1").build(), ImmutableList.of(repository));
    }
  }

  @Override
  public Optional<InputStream> read(final Repository repository, final String path) {
    return findAssetByName(repository, path).map(asset -> {
      try (StorageTx tx = repository.facet(StorageFacet.class).txSupplier().get()) {
        tx.begin();
        return tx.requireBlob(asset.blobRef()).getInputStream();
      }
    });
  }

  @Override
  public void deleteAssetBlob(final Repository repository, final String assetPath) {
    StorageFacet storage = repository.facet(StorageFacet.class);
    StorageTx tx = storage.txSupplier().get();
    try {
      tx.begin();

      Asset packageRootAsset = tx.findAssetWithProperty("name", assetPath, tx.findBucket(repository));
      storage.blobStore().delete(packageRootAsset.blobRef().getBlobId(), "test merge recovery");
    }
    finally {
      tx.close();
    }
  }

  @Override
  public BlobRef getBlobRefOfAsset(final Repository repository, final String path) {
    Optional<Asset> optionalAsset = findAssetByName(repository, path);
    return optionalAsset.map(Asset::blobRef).orElse(null);
  }

  @Override
  public EntityId getComponentId(final Repository repository, final String assetPath) {
    return findAssetByNameNotNull(repository, assetPath).componentId();
  }

  @Override
  public NestedAttributesMap getAttributes(final Repository repository) {
    return repository.facet(AttributesFacet.class).getAttributes();
  }

  @Override
  public void modifyAttributes(final Repository repository, String child1, final String child2, final int value) {
    AttributesFacet facet = repository.facet(AttributesFacet.class);
    facet.modifyAttributes(attribute -> attribute.child(child1).child(child2).set(Integer.class, value));
  }
}
