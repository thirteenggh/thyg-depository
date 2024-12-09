package org.sonatype.nexus.blobstore.restore.maven.internal;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.restore.datastore.BaseRestoreBlobStrategy;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.log.DryRunPrefix;
import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.view.payloads.BlobPayload;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.blobstore.api.BlobAttributesConstants.HEADER_PREFIX;
import static org.sonatype.nexus.blobstore.api.BlobStore.CONTENT_TYPE_HEADER;

/**
 * @since 3.29
 */
@FeatureFlag(name = "nexus.datastore.enabled")
@Named(Maven2Format.NAME)
@Singleton
public class MavenRestoreBlobStrategy
    extends BaseRestoreBlobStrategy<MavenRestoreBlobData>
{
  private final RepositoryManager repositoryManager;

  private final MavenPathParser mavenPathParser;

  @Inject
  protected MavenRestoreBlobStrategy(
      final DryRunPrefix dryRunPrefix,
      final RepositoryManager repositoryManager,
      final MavenPathParser mavenPathParser)
  {
    super(dryRunPrefix);
    this.repositoryManager = checkNotNull(repositoryManager);
    this.mavenPathParser = checkNotNull(mavenPathParser);
  }

  @Override
  protected boolean canAttemptRestore(@Nonnull final MavenRestoreBlobData data) {
    MavenPath mavenPath = data.getMavenPath();
    Repository repository = data.getRepository();

    if (mavenPath.getCoordinates() == null && !mavenPathParser.isRepositoryMetadata(mavenPath)) {
      log.warn(
          "Skipping blob in repository named {}, because no maven coordinates found for blob named {} in blob store named {} and the blob not maven metadata",
          repository.getName(),
          data.getBlobName(),
          data.getBlobStore().getBlobStoreConfiguration().getName());
      return false;
    }

    Optional<MavenContentFacet> mavenFacet = repository.optionalFacet(MavenContentFacet.class);

    if (!mavenFacet.isPresent()) {
      if (log.isWarnEnabled()) {
        log.warn("Skipping as Maven Content Facet not found on repository: {}", repository.getName());
      }
      return false;
    }

    return true;
  }

  @Override
  protected void createAssetFromBlob(final Blob assetBlob, final MavenRestoreBlobData data) throws IOException {
    String contentType = data.getProperty(HEADER_PREFIX + CONTENT_TYPE_HEADER);
    MavenContentFacet mavenFacet = data.getRepository().facet(MavenContentFacet.class);
    mavenFacet.put(data.getMavenPath(), new BlobPayload(assetBlob, contentType));
  }

  @Override
  protected String getAssetPath(@Nonnull final MavenRestoreBlobData data) {
    return data.getMavenPath().getPath();
  }

  @Override
  protected MavenRestoreBlobData createRestoreData(
      final Properties properties,
      final Blob blob,
      final BlobStore blobStore)
  {
    return new MavenRestoreBlobData(blob, properties, blobStore, repositoryManager, mavenPathParser);
  }

  @Override
  protected boolean isComponentRequired(final MavenRestoreBlobData data) {
    MavenPath path = data.getMavenPath();
    return !(mavenPathParser.isRepositoryIndex(path) || mavenPathParser.isRepositoryMetadata(path));
  }

  @Override
  public void after(final boolean updateAssets, final Repository repository) {
    //no-op
  }
}
