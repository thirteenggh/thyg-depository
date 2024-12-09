package org.sonatype.nexus.blobstore.restore.raw.internal.orient;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.orient.OrientBaseRestoreBlobStrategy;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.common.log.DryRunPrefix;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.orient.raw.RawContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Query;

import com.google.common.collect.ImmutableList;

import static org.sonatype.nexus.common.hash.HashAlgorithm.MD5;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;

/**
 * @since 3.15
 */
@Named("raw")
@Priority(Integer.MAX_VALUE)
@Singleton
public class OrientRawRestoreBlobStrategy
    extends OrientBaseRestoreBlobStrategy<RawRestoreBlobData>
{
  private final AttributesMap NO_CONTENT_ATTRIBUTES = null;

  @Inject
  public OrientRawRestoreBlobStrategy(
      final NodeAccess nodeAccess,
      final RepositoryManager repositoryManager,
      final BlobStoreManager blobStoreManager,
      final DryRunPrefix dryRunPrefix)
  {
    super(nodeAccess, repositoryManager, blobStoreManager, dryRunPrefix);
  }

  @Override
  protected List<HashAlgorithm> getHashAlgorithms() {
    return ImmutableList.of(SHA1, MD5);
  }

  @Override
  protected RawRestoreBlobData createRestoreData(final RestoreBlobData blobData) {
    return new RawRestoreBlobData(blobData);
  }

  @Override
  protected boolean canAttemptRestore(final RawRestoreBlobData data) {
    Repository repository = data.getBlobData().getRepository();

    if (!repository.optionalFacet(RawContentFacet.class).isPresent()) {
      log.warn("Skipping as Raw Facet not found on repository: {}", repository.getName());
      return false;
    }

    return true;
  }

  @Override
  protected String getAssetPath(final RawRestoreBlobData data) {
    return data.getBlobData().getBlobName();
  }

  @Override
  protected boolean assetExists(final RawRestoreBlobData data) {
    return getRawContentFacet(data).assetExists(data.getBlobData().getBlobName());
  }

  @Override
  protected void createAssetFromBlob(final AssetBlob assetBlob, final RawRestoreBlobData data) {
    getRawContentFacet(data).put(getAssetPath(data), assetBlob, NO_CONTENT_ATTRIBUTES);
  }

  private RawContentFacet getRawContentFacet(final RawRestoreBlobData data) {
    return data.getBlobData().getRepository().facet(RawContentFacet.class);
  }

  @Override
  protected boolean componentRequired(@Nonnull final RawRestoreBlobData data) throws IOException {
    return true;
  }

  @Override
  protected Query getComponentQuery(@Nonnull final RawRestoreBlobData data) {
    return Query.builder().where(P_NAME).eq(data.getBlobData().getBlobName()).build();
  }

  @Override
  protected Repository getRepository(@Nonnull final RawRestoreBlobData data) {
    return data.getBlobData().getRepository();
  }
}
