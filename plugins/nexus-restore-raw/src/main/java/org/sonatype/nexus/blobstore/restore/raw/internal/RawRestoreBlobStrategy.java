package org.sonatype.nexus.blobstore.restore.raw.internal;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.datastore.BaseRestoreBlobStrategy;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.log.DryRunPrefix;
import org.sonatype.nexus.content.raw.RawContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.view.payloads.BlobPayload;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.blobstore.api.BlobAttributesConstants.HEADER_PREFIX;
import static org.sonatype.nexus.blobstore.api.BlobStore.CONTENT_TYPE_HEADER;

/**
 * @since 3.29
 */
@Named("raw")
@FeatureFlag(name = "nexus.datastore.enabled")
@Singleton
public class RawRestoreBlobStrategy
    extends BaseRestoreBlobStrategy<RestoreBlobData>
{
  private final RepositoryManager repositoryManager;

  @Inject
  public RawRestoreBlobStrategy(
      final DryRunPrefix dryRunPrefix,
      final RepositoryManager repositoryManager)
  {
    super(dryRunPrefix);
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Override
  protected boolean canAttemptRestore(@Nonnull final RestoreBlobData data)
  {
    Repository repository = data.getRepository();

    if (repository.optionalFacet(RawContentFacet.class).isPresent()) {
      return true;
    }
    else {
      log.warn("Skipping as Raw Facet not found on repository: {}", repository.getName());
      return false;
    }
  }

  @Override
  protected void createAssetFromBlob(final Blob assetBlob, final RestoreBlobData data) throws IOException
  {
    String contentType = data.getProperty(HEADER_PREFIX + CONTENT_TYPE_HEADER);
    RawContentFacet rawContentFacet = data.getRepository().facet(RawContentFacet.class);
    rawContentFacet.put(data.getBlobName(), new BlobPayload(assetBlob, contentType));
  }

  @Override
  protected String getAssetPath(@Nonnull final RestoreBlobData data) {
    return data.getBlobName();
  }

  @Override
  protected RestoreBlobData createRestoreData(
      final Properties properties,
      final Blob blob,
      final BlobStore blobStore)
  {
    return new RestoreBlobData(blob, properties, blobStore, repositoryManager);
  }

  @Override
  protected boolean isComponentRequired(final RestoreBlobData data) {
    return true;
  }

  @Override
  public void after(final boolean updateAssets, final Repository repository) {
    //no-op
  }
}
