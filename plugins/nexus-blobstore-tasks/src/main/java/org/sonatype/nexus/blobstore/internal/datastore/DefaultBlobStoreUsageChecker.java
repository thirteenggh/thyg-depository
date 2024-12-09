package org.sonatype.nexus.blobstore.internal.datastore;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobId;
import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreUsageChecker;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import com.codahale.metrics.annotation.Timed;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.of;
import static org.sonatype.nexus.blobstore.api.BlobStore.REPO_NAME_HEADER;

/**
 * Check if a blob is referenced in the corresponding metadata for NewDB
 *
 * @since 3.29
 */
@Named
@Singleton
@FeatureFlag(name = "nexus.datastore.enabled")
public class DefaultBlobStoreUsageChecker
    implements BlobStoreUsageChecker
{
  private final RepositoryManager repositoryManager;

  @Inject
  public DefaultBlobStoreUsageChecker(final RepositoryManager repositoryManager)
  {
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Override
  @Timed
  public boolean test(final BlobStore blobStore, final BlobId blobId, final String blobName) {
    return of(blobId)
        .map(blobStore::get)
        .map(Blob::getHeaders)
        .map(headers -> headers.get(REPO_NAME_HEADER))
        .map(repositoryManager::get)
        .map(repository -> (ContentFacetSupport) repository.facet(ContentFacet.class))
        .flatMap(contentFacetSupport -> {
          String blobStoreName = blobStore.getBlobStoreConfiguration().getName();
          BlobRef blobRef = new BlobRef(contentFacetSupport.nodeName(), blobStoreName, blobId.asUniqueString());
          return contentFacetSupport.stores().assetBlobStore.readAssetBlob(blobRef);
        })
        .isPresent();
  }
}
