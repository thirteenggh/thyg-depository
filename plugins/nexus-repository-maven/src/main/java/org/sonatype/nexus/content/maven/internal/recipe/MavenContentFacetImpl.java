package org.sonatype.nexus.content.maven.internal.recipe;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.content.maven.internal.event.RebuildMavenArchetypeCatalogEvent;
import org.sonatype.nexus.content.maven.store.Maven2ComponentStore;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationFacet;
import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.RepositoryContent;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssetBuilder;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.maintenance.ContentMaintenanceFacet;
import org.sonatype.nexus.repository.content.store.FormatStoreManager;
import org.sonatype.nexus.repository.maven.LayoutPolicy;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPath.Coordinates;
import org.sonatype.nexus.repository.maven.MavenPath.HashType;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.maven.VersionPolicy;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.maven.internal.validation.MavenMetadataContentValidator;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.content.maven.internal.recipe.MavenArchetypeCatalogFacetImpl.MAVEN_ARCHETYPE_KIND;
import static org.sonatype.nexus.content.maven.internal.recipe.MavenAttributesHelper.assetKind;
import static org.sonatype.nexus.content.maven.internal.recipe.MavenAttributesHelper.setMavenAttributes;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW_ONCE;
import static org.sonatype.nexus.repository.maven.internal.Attributes.AssetKind.REPOSITORY_INDEX;
import static org.sonatype.nexus.repository.maven.internal.Attributes.AssetKind.REPOSITORY_METADATA;
import static org.sonatype.nexus.repository.maven.internal.Constants.METADATA_FILENAME;
import static org.sonatype.nexus.repository.maven.internal.MavenModels.readModel;

/**
 * A {@link MavenContentFacet} that persists to a {@link ContentFacet}.
 *
 * @since 3.25
 */
@Named(Maven2Format.NAME)
public class MavenContentFacetImpl
    extends ContentFacetSupport
    implements MavenContentFacet
{
  private static final char ASSET_PATH_PREFIX = '/';

  private static final String CONFIG_KEY = "maven";

  private final Map<String, MavenPathParser> mavenPathParsers;

  private final MavenMetadataContentValidator metadataValidator;

  private final EventManager eventManager;

  private final boolean metadataValidationEnabled;

  private Config config;

  private MavenPathParser mavenPathParser;

  static class Config
  {
    @NotNull(groups = {HostedType.ValidationGroup.class, ProxyType.ValidationGroup.class})
    public VersionPolicy versionPolicy;

    @NotNull(groups = {HostedType.ValidationGroup.class, ProxyType.ValidationGroup.class})
    public LayoutPolicy layoutPolicy;

    @Override
    public String toString() {
      return getClass().getSimpleName() + "{" +
          "versionPolicy=" + versionPolicy +
          ", layoutPolicy=" + layoutPolicy +
          '}';
    }
  }

  @Inject
  public MavenContentFacetImpl(
      @Named(Maven2Format.NAME) final FormatStoreManager formatStoreManager,
      final Map<String, MavenPathParser> mavenPathParsers,
      final MavenMetadataContentValidator metadataValidator,
      final EventManager eventManager,
      @Named("${nexus.maven.metadata.validation.enabled:-true}") final boolean metadataValidationEnabled)
  {
    super(formatStoreManager);
    this.mavenPathParsers = checkNotNull(mavenPathParsers);
    this.metadataValidator = metadataValidator;
    this.eventManager = eventManager;
    this.metadataValidationEnabled = metadataValidationEnabled;
  }

  @Override
  public MavenPathParser getMavenPathParser() {
    return mavenPathParser;
  }

  @Override
  public LayoutPolicy layoutPolicy() {
    return config.layoutPolicy;
  }

  @Override
  public VersionPolicy getVersionPolicy() {
    return config.versionPolicy;
  }

  @Override
  protected void doInit(final Configuration configuration) throws Exception {
    super.doInit(configuration);
    mavenPathParser = checkNotNull(mavenPathParsers.get(getRepository().getFormat().getValue()));
  }

  @Override
  protected void doConfigure(final Configuration configuration) throws Exception {
    super.doConfigure(configuration);
    config = facet(ConfigurationFacet.class).readSection(configuration, CONFIG_KEY, MavenContentFacetImpl.Config.class);
    log.debug("Config: {}", config);
  }

  @Override
  protected WritePolicy writePolicy(final Asset asset) {
    WritePolicy configuredWritePolicy = super.writePolicy(asset);
    if (ALLOW_ONCE == configuredWritePolicy) {
      String assetKind = asset.kind();
      if (StringUtils.equals(REPOSITORY_METADATA.name(), assetKind)
          || StringUtils.equals(REPOSITORY_INDEX.name(), assetKind)) {
        return ALLOW;
      }
    }
    return configuredWritePolicy;
  }

  @Override
  public Optional<Content> get(final MavenPath mavenPath) {
    log.debug("GET {} : {}", getRepository().getName(), mavenPath);

    return findAsset(assetPath(mavenPath))
        .map(FluentAsset::download);
  }

  @Override
  public Content put(final MavenPath mavenPath, final Payload content) throws IOException {
    log.debug("PUT {} : {}", getRepository().getName(), mavenPath);

    try (TempBlob blob = blobs().ingest(content, HashType.ALGORITHMS)) {
      if (isMetadataAndValidationEnabled(mavenPath)) {
        validate(mavenPath, blob);
      }
      return save(mavenPath, content, blob);
    }
  }

  private Optional<FluentAsset> findAsset(final String path) {
    return assets()
        .path(path)
        .find();
  }

  private boolean isMetadataAndValidationEnabled(final MavenPath mavenPath) {
    return mavenPath.getFileName().equals(METADATA_FILENAME) && metadataValidationEnabled;
  }

  private void validate(final MavenPath mavenPath, final TempBlob blob) {
    log.debug("Validating maven-metadata.xml before storing");
    metadataValidator.validate(mavenPath.getPath(), blob.get());
  }

  private Content save(final MavenPath mavenPath, final Payload content, final TempBlob blob) throws IOException {
    FluentComponent component = null;
    if (mavenPath.getCoordinates() != null) {
      Optional<Model> model = maybeReadMavenModel(mavenPath, blob);
      component = createOrGetComponent(mavenPath, model);
    }
    return createOrUpdateAsset(mavenPath, component, content, blob);
  }

  private FluentComponent createOrGetComponent(final MavenPath mavenPath, final Optional<Model> model)
  {
    Optional<String> optionalKind = model.map(MavenAttributesHelper::getPackaging);
    Coordinates coordinates = mavenPath.getCoordinates();

    FluentComponent component = components()
        .name(coordinates.getArtifactId())
        .namespace(coordinates.getGroupId())
        .version(coordinates.getVersion())
        .kind(optionalKind)
        .getOrCreate();

    boolean isNew = isNewRepositoryContent(component);
    MavenAttributesHelper.setMavenAttributes(
          (Maven2ComponentStore) stores().componentStore, component, coordinates, model, contentRepositoryId());

    if (isNew) {
      publishEvents(component);
    }
    else {
      // kind isn't set for existing components
      optionalKind.ifPresent(component::kind);
    }
    return component;
  }

  private boolean isNewRepositoryContent(final RepositoryContent repositoryContent) {
    return repositoryContent.attributes().isEmpty();
  }

  @Override
  public void maybeUpdateComponentAttributes(final MavenPath mavenPath) throws IOException
  {
    if (mavenPath.isPom()) {
      Optional<FluentAsset> optAsset = assets().path(assetPath(mavenPath)).find();
      if (optAsset.isPresent()) {
        FluentAsset asset = optAsset.get();
        Model model = readModel(asset.download().openInputStream());
        FluentComponent component = createOrGetComponent(mavenPath, Optional.ofNullable(model));
        publishEvents(component);
      }
    }
  }

  private void publishEvents(final FluentComponent component) {
    if (MAVEN_ARCHETYPE_KIND.equals(component.kind())) {
      eventManager.post(new RebuildMavenArchetypeCatalogEvent(getRepository().getName()));
    }
  }

  private Optional<Model> maybeReadMavenModel(final MavenPath mavenPath, final TempBlob blob) throws IOException
  {
    Model model = null;
    if (mavenPath.isPom()) {
      model = readModel(blob.getBlob().getInputStream());
      if (model == null) {
        log.warn("Could not parse POM: {} @ {}", getRepository().getName(), assetPath(mavenPath));
      }
    }
    return Optional.ofNullable(model);
  }

  private Content createOrUpdateAsset(
      final MavenPath mavenPath,
      final Component component,
      final Payload content,
      final TempBlob blob)
  {
    String path = assetPath(mavenPath);
    FluentAssetBuilder assetBuilder = assets().path(path).kind(assetKind(mavenPath, mavenPathParser));
    if (component != null) {
      assetBuilder = assetBuilder.component(component);
    }

    FluentAsset asset = assetBuilder.getOrCreate();
    if (isNewRepositoryContent(asset)) {
      setMavenAttributes(asset, mavenPath);
    }

    return asset.attach(blob)
        .markAsCached(content)
        .download();
  }

  private String assetPath(final MavenPath mavenPath) {
    return ASSET_PATH_PREFIX + mavenPath.getPath();
  }

  @Override
  public boolean delete(final MavenPath mavenPath) {
    log.trace("DELETE {} : {}", getRepository().getName(), mavenPath);
    boolean assetIsDeleted = deleteAsset(mavenPath);
    if (assetIsDeleted && mavenPath.getCoordinates() != null) {
      maybeDeleteComponent(mavenPath.getCoordinates());
    }
    return assetIsDeleted;
  }

  @Override
  public boolean delete(final List<String> paths) {
    Repository repository = getRepository();
    log.trace("DELETE {} assets at {}", repository.getName(), paths);
    return stores().assetStore.deleteAssetsByPaths(contentRepositoryId(), paths);
  }

  @Override
  public Set<String> deleteWithHashes(final MavenPath mavenPath) {
    final Set<String> paths = new HashSet<>(HashType.values().length + 1);
    if (delete(mavenPath.main())) {
      paths.add(mavenPath.main().getPath());
    }
    for (HashType hashType : HashType.values()) {
      MavenPath hashMavenPath = mavenPath.main().hash(hashType);
      if (delete(hashMavenPath)) {
        paths.add(hashMavenPath.getPath());
      }
    }
    return paths;
  }

  @Override
  public boolean exists(final MavenPath mavenPath) {
    return findAsset(assetPath(mavenPath)).isPresent();
  }

  private boolean deleteAsset(final MavenPath mavenPath) {
    return findAsset(assetPath(mavenPath))
        .map(FluentAsset::delete)
        .orElse(false);
  }

  private void maybeDeleteComponent(final Coordinates coordinates) {
    components()
        .name(coordinates.getArtifactId())
        .namespace(coordinates.getGroupId())
        .version(coordinates.getVersion())
        .find()
        .ifPresent(this::deleteIfNoAssetsLeft);
  }

  private void deleteIfNoAssetsLeft(final FluentComponent component) {
    if (component.assets().isEmpty()) {
      component.delete();
      publishEvents(component);
      ((MavenMaintenanceFacet) repository().facet(ContentMaintenanceFacet.class)).deleteMetadata(component);
    }
  }
}
