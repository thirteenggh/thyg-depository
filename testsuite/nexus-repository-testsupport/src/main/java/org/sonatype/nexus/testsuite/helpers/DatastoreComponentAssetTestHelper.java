package org.sonatype.nexus.testsuite.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.common.collect.ImmutableNestedAttributesMap;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.time.DateHelper;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.event.asset.AssetDownloadedEvent;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.maintenance.ContentMaintenanceFacet;
import org.sonatype.nexus.repository.content.store.ContentStoreEvent;
import org.sonatype.nexus.repository.content.store.InternalIds;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import static java.time.LocalDate.now;
import static org.apache.commons.lang.StringUtils.stripStart;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.sonatype.nexus.blobstore.api.BlobStoreManager.DEFAULT_BLOBSTORE_NAME;
import static org.sonatype.nexus.datastore.api.DataStoreManager.CONTENT_DATASTORE_NAME;
import static org.sonatype.nexus.repository.content.AttributeOperation.OVERLAY;

@Named
@Singleton
public class DatastoreComponentAssetTestHelper
    implements ComponentAssetTestHelper
{
  private static final String SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT";

  private static final DateTimeFormatter YEAR_MONTH_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

  @Inject
  private RepositoryManager repositoryManager;

  @Inject
  private DataSessionSupplier sessionSupplier;

  @Inject
  private EventManager eventManager;

  @Inject
  private BlobStoreManager blobStoreManager;

  @Override
  public DateTime getBlobCreatedTime(final Repository repository, final String path) {
    return findAssetByPathNotNull(repository, path)
        .blob()
        .map(AssetBlob::blobCreated)
        .map(DateHelper::toDateTime)
        .orElse(null);
  }

  @Override
  public DateTime getAssetCreatedTime(final Repository repository, final String path) {
    return DateHelper.toDateTime(findAssetByPathNotNull(repository, path).created());
  }

  @Override
  public DateTime getBlobUpdatedTime(final Repository repository, final String path) {
    // AssetBlobs are immutable so the created time is the equivalent of the old updated time.
    return getBlobCreatedTime(repository, path);
  }

  @Override
  public DateTime getLastDownloadedTime(final Repository repository, final String path) {
    return findAssetByPathNotNull(repository, path)
        .lastDownloaded()
        .map(DateHelper::toDateTime)
        .orElse(null);
  }

  @Override
  public void deleteComponent(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    Component component = findComponent(repository, namespace, name, version);
    repository.facet(ContentMaintenanceFacet.class).deleteComponent(component);
  }

  private static FluentComponent findComponent(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    return repository.facet(ContentFacet.class).components()
        .name(name)
        .namespace(namespace)
        .version(version)
        .find()
        .orElseThrow(() -> new ComponentNotFoundException(repository, namespace, name, version));
  }

  private static FluentComponent findComponent(
      final Repository repository,
      final String namespace,
      final String name)
  {
    return repository.facet(ContentFacet.class).components()
        .name(name)
        .namespace(namespace)
        .find()
        .orElseThrow(() -> new ComponentNotFoundException(repository, namespace, name, null));
  }

  private Asset findAssetByPathNotNull(final Repository repository, final String path) {
    return findAssetByPath(repository, path).orElseThrow(() -> new AssetNotFoundException(repository, path));
  }

  private Optional<FluentAsset> findAssetByPath(final Repository repository, final String path) {
    return repository.facet(ContentFacet.class).assets()
        .path(adjustedPath(path))
        .find();
  }

  @Override
  public List<String> findAssetPaths(final String repositoryName) {
    return repositoryManager.get(repositoryName).facet(ContentFacet.class).assets()
        .browse(Integer.MAX_VALUE, null)
        .stream()
        .map(Asset::path)
        .collect(Collectors.toList());
  }

  @Override
  public String contentTypeFor(final String repositoryName, final String path) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void removeAsset(final Repository repository, final String path) {
    ContentMaintenanceFacet contentMaintenanceFacet = repository.facet(ContentMaintenanceFacet.class);
    findAssetByPath(repository, path).ifPresent(contentMaintenanceFacet::deleteAsset);
  }

  @Override
  public int countAssets(final Repository repository) {
    return repository.facet(ContentFacet.class).assets().browse(Integer.MAX_VALUE, null).size();
  }

  @Override
  public int countComponents(final Repository repository) {
    return repository.facet(ContentFacet.class).components().browse(Integer.MAX_VALUE, null).size();
  }

  @Override
  public NestedAttributesMap attributes(final Repository repository, final String path) {
    return findAssetByPathNotNull(repository, path).attributes();
  }

  @Override
  public boolean assetExists(final Repository repository, final String path) {
    return findAssetByPath(repository, path).isPresent();
  }

  @Override
  public boolean componentExists(final Repository repository, final String name) {
    List<FluentComponent> components = browseComponents(repository);
    return components.stream().anyMatch(comp -> comp.name().equals(name));
  }

  @Override
  public boolean componentExists(final Repository repository, final String name, final String version) {
    if (endsWith(version, SNAPSHOT_VERSION_SUFFIX)) {
      return findSnapshotComponent(repository, name, version).isPresent();
    }
    else {
      List<FluentComponent> components = browseComponents(repository);
      return components.stream().anyMatch(comp -> comp.name().equals(name) && comp.version().equals(version));
    }
  }

  private Optional<FluentComponent> findSnapshotComponent(
      final Repository repository,
      final String name,
      final String version)
  {
    List<FluentComponent> components = browseComponents(repository);
    String gav = substring(version, 0, indexOf(version, SNAPSHOT_VERSION_SUFFIX));
    String versionWithDate = String.format("%s-%s", gav, now().format(YEAR_MONTH_DAY_FORMAT));
    return components.stream()
        .filter(comp -> comp.name().equals(name))
        .filter(comp -> startsWith(comp.version(), versionWithDate))
        .findAny();
  }

  private List<FluentComponent> browseComponents(final Repository repository) {
    List<FluentComponent> componentsFound = new ArrayList<>();
    ContentFacet facet = repository.facet(ContentFacet.class);
    Continuation<FluentComponent> components = facet.components().browse(10, null);
    while (!components.isEmpty()) {
      componentsFound.addAll(components);
      components = facet.components().browse(10, components.nextContinuationToken());
    }
    return componentsFound;
  }

  @Override
  public boolean componentExists(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    if (endsWith(version, SNAPSHOT_VERSION_SUFFIX)) {
      return findSnapshotComponent(repository, name, version).isPresent();
    }
    else {
      List<FluentComponent> components = browseComponents(repository);
      return components.stream().anyMatch(comp -> comp.namespace().equals(namespace) && comp.name().equals(name) && comp.version().equals(version));
    }
  }

  @Override
  public boolean componentExistsWithAssetPathMatching(
      final Repository repository,
      final Predicate<String> pathMatcher)
  {
    return repository.facet(ContentFacet.class).assets().browse(Integer.MAX_VALUE, null)
        .stream()
        .filter(asset -> asset.component().isPresent())
        .map(FluentAsset::path)
        .map(this::adjustedPath)
        .anyMatch(pathMatcher);
  }

  @Override
  public boolean assetWithComponentExists(
      final Repository repository,
      final String path,
      final String namespace,
      final String name)
  {
    Collection<FluentAsset> assets = findComponent(repository, namespace, this.adjustedPath(name)).assets();

    return assets.stream()
        .map(FluentAsset::path)
        .anyMatch(adjustedPath(path)::equals);
  }

  @Override
  public boolean assetWithComponentExists(
      final Repository repository,
      final String path,
      final String namespace,
      final String name,
      final String version)
  {
    Collection<FluentAsset> assets = findComponent(repository, namespace, name, version).assets();

    return assets.stream()
        .map(FluentAsset::path)
        .anyMatch(adjustedPath(path)::equals);
  }

  @Override
  public boolean assetWithoutComponentExists(final Repository repository, final String path) {
    return !findAssetByPathNotNull(repository, path).component().isPresent();
  }

  @Override
  public NestedAttributesMap componentAttributes(
      final Repository repository,
      final String namespace,
      final String name,
      final String version)
  {
    return findComponent(repository, namespace, name, version).attributes();
  }

  @Override
  public NestedAttributesMap componentAttributes(
      final Repository repository,
      final String namespace,
      final String name)
  {
    return findComponent(repository, namespace, adjustedPath(name)).attributes();
  }

  @Override
  public String adjustedPath(final String path) {
    return "/" + stripStart(path, "/");
  }

  @Override
  public void setLastDownloadedTime(final Repository repository, final int minusSeconds) {
    int repositoryId = ((ContentFacetSupport) repository.facet(ContentFacet.class)).contentRepositoryId();

    Timestamp time = Timestamp.from(LocalDateTime.now().minusSeconds(minusSeconds).toInstant(ZoneOffset.UTC));

    try (Connection connection = sessionSupplier.openConnection(CONTENT_DATASTORE_NAME);
        PreparedStatement stmt = connection.prepareStatement("UPDATE " + repository.getFormat().getValue() + "_asset "
            + "SET last_downloaded = ? WHERE repository_id = ?")) {
      stmt.setTimestamp(1, time);
      stmt.setInt(2, repositoryId);
      stmt.execute();
      if(stmt.getWarnings() != null) {
        throw new RuntimeException("Failed to set download time: " + stmt.getWarnings());
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    repository.facet(ContentFacet.class).assets().browse(Integer.MAX_VALUE, null).stream()
        .forEach(asset -> sendEvent(repository, asset));
  }

  @Override
  public void setLastDownloadedTime(
      final Repository repository,
      final int minusSeconds,
      final Predicate<String> pathMatcher)
  {
    int repositoryId = ((ContentFacetSupport) repository.facet(ContentFacet.class)).contentRepositoryId();

    List<String> pathes = repository.facet(ContentFacet.class).assets().browse(Integer.MAX_VALUE, null).stream()
        .map(FluentAsset::path)
        .filter(pathMatcher)
        .collect(Collectors.toList());

    Timestamp time = Timestamp.from(LocalDateTime.now().minusSeconds(minusSeconds).toInstant(ZoneOffset.UTC));

    try (Connection connection = sessionSupplier.openConnection(CONTENT_DATASTORE_NAME);
        PreparedStatement stmt = connection.prepareStatement("UPDATE " + repository.getFormat().getValue() + "_asset "
            + "SET last_downloaded = ? "
            + "WHERE repository_id = ? AND path = ?")) {

      for (String path : pathes) {
        stmt.setTimestamp(1, time);
        stmt.setInt(2, repositoryId);
        stmt.setString(3, path);

        stmt.execute();
        if(stmt.getWarnings() != null) {
          throw new RuntimeException("Failed to set download time: " + stmt.getWarnings());
        }
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }

    pathes.stream().map(path -> findAssetByPath(repository, path))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(asset -> sendEvent(repository, asset));
  }

  @Override
  public Optional<InputStream> read(final Repository repository, final String path) {
    return findAssetByPath(repository, path)
        .map(FluentAsset::download)
        .map(content -> {
          try {
            return content.openInputStream();
          }
          catch (IOException e) {
            throw new UncheckedIOException(e);
          }
        });
  }

  private void sendEvent(final Repository repository, final FluentAsset asset) {
    try {
      asset.component().isPresent(); // prime it
      AssetDownloadedEvent event = new AssetDownloadedEvent(asset);
      Method method = ContentStoreEvent.class.getDeclaredMethod("setRepository", Repository.class);
      method.setAccessible(true);
      method.invoke(event, repository);
      eventManager.post(event);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAssetBlob(final Repository repository, final String assetPath) {
    BlobStore blobStore = blobStoreManager.get(DEFAULT_BLOBSTORE_NAME);
    findAssetByPath(repository, assetPath)
        .flatMap(Asset::blob)
        .map(AssetBlob::blobRef)
        .map(BlobRef::getBlobId)
        .ifPresent(blobId -> blobStore.delete(blobId, "test merge recovery"));
  }

  @Override
  public BlobRef getBlobRefOfAsset(final Repository repository, final String path) {
    Optional<FluentAsset> optionalAsset = findAssetByPath(repository, path);
    return optionalAsset.flatMap(FluentAsset::blob).map(AssetBlob::blobRef).orElse(null);
  }

  @Override
  public EntityId getComponentId(final Repository repository, final String assetPath) {
    Optional<Component> component = findAssetByPath(repository, assetPath)
        .flatMap(FluentAsset::component);
    return component
        .map(InternalIds::internalComponentId)
        .map(InternalIds::toExternalId)
        .orElseThrow(() -> new ComponentNotFoundException(repository, component.map(Component::namespace).orElse(EMPTY),
            component.map(Component::name).orElse(EMPTY), component.map(Component::version).orElse(EMPTY)));
  }

  @Override
  public NestedAttributesMap getAttributes(final Repository repository) {
    NestedAttributesMap attr = repository.facet(ContentFacet.class).attributes();
    return new ImmutableNestedAttributesMap(attr.getParent(), attr.getKey(), attr.backing());
  }

  @Override
  public void modifyAttributes(final Repository repository, String child1, final String child2, final int value) {
    repository.facet(ContentFacet.class)
        .attributes(OVERLAY, child1, ImmutableMap.of(child2, ImmutableMap.of(Integer.class, value)));
  }
}
