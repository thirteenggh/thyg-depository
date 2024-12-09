package org.sonatype.nexus.blobstore.restore.maven.internal.orient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.orient.OrientBaseRestoreBlobStrategy;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.common.log.DryRunPrefix;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.orient.maven.OrientMavenFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPath.Coordinates;
import org.sonatype.nexus.repository.maven.MavenPath.HashType;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Query;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.repository.transaction.TransactionalTouchBlob;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.sonatype.nexus.common.hash.HashAlgorithm.MD5;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_GROUP;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_VERSION;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;

/**
 * @since 3.4
 */
@FeatureFlag(name = "nexus.orient.store.content")
@Priority(Integer.MAX_VALUE)
@Named("maven2")
@Singleton
public class OrientMavenRestoreBlobStrategy
    extends OrientBaseRestoreBlobStrategy<MavenRestoreBlobData>
{
  private final MavenPathParser mavenPathParser;

  @Inject
  public OrientMavenRestoreBlobStrategy(
      final MavenPathParser mavenPathParser,
      final NodeAccess nodeAccess,
      final RepositoryManager repositoryManager,
      final BlobStoreManager blobStoreManager,
      final DryRunPrefix dryRunPrefix)
  {
    super(nodeAccess, repositoryManager, blobStoreManager, dryRunPrefix);
    this.mavenPathParser = checkNotNull(mavenPathParser);
  }

  @Override
  protected MavenRestoreBlobData createRestoreData(final RestoreBlobData blobData) {
    return new MavenRestoreBlobData(blobData, mavenPathParser.parsePath(blobData.getBlobName()));
  }

  @Override
  protected boolean canAttemptRestore(@Nonnull final MavenRestoreBlobData data) {
    MavenPath mavenPath = data.getMavenPath();
    RestoreBlobData blobData = data.getBlobData();
    Repository repository = blobData.getRepository();

    if (mavenPath.getCoordinates() == null && !mavenPathParser.isRepositoryMetadata(mavenPath)) {
      if (log.isWarnEnabled()) {
        log.warn(
            "Skipping blob in repository named {}, because no maven coordinates found for blob named {} in blob store named {} and the blob not maven metadata",
            repository.getName(),
            blobData.getBlobName(),
            blobData.getBlobStore().getBlobStoreConfiguration().getName());
      }
      return false;
    }

    Optional<OrientMavenFacet> mavenFacet = repository.optionalFacet(OrientMavenFacet.class);

    if (!mavenFacet.isPresent()) {
      if (log.isWarnEnabled()) {
        log.warn("Skipping as Maven Facet not found on repository: {}", repository.getName());
      }
      return false;
    }

    return true;
  }

  @Override
  @Nonnull
  protected List<HashAlgorithm> getHashAlgorithms() {
    return HashType.ALGORITHMS;
  }

  @Override
  protected String getAssetPath(@Nonnull final MavenRestoreBlobData data) {
    return data.getMavenPath().getPath();
  }

  @Override
  @TransactionalTouchBlob
  protected boolean assetExists(@Nonnull final MavenRestoreBlobData data) throws IOException {
    return data.getBlobData().getRepository().facet(OrientMavenFacet.class).get(data.getMavenPath()) != null;
  }

  @Override
  protected boolean componentRequired(@Nonnull final MavenRestoreBlobData data) throws IOException {
    MavenPath path = data.getMavenPath();
    return !(mavenPathParser.isRepositoryIndex(path) || mavenPathParser.isRepositoryMetadata(path));
  }

  @Override
  protected Query getComponentQuery(@Nonnull final MavenRestoreBlobData data) {
    Coordinates coordinates = data.getMavenPath().getCoordinates();
    if (coordinates != null) {
      return Query.builder()
          .where(P_GROUP).eq(coordinates.getGroupId())
          .and(P_NAME).eq(coordinates.getArtifactId())
          .and(P_VERSION).eq(coordinates.getVersion())
          .build();
    }
    return null;
  }

  @Override
  @TransactionalStoreMetadata
  protected void createAssetFromBlob(@Nonnull final AssetBlob assetBlob, @Nonnull final MavenRestoreBlobData data)
      throws IOException
  {
    data.getBlobData().getRepository().facet(OrientMavenFacet.class).put(data.getMavenPath(), assetBlob, null);
  }

  @Override
  protected Repository getRepository(@Nonnull final MavenRestoreBlobData data) {
    return data.getBlobData().getRepository();
  }
}
