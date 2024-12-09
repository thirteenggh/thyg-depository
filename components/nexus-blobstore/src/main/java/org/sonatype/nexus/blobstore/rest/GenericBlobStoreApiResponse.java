package org.sonatype.nexus.blobstore.rest;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.api.BlobStoreMetrics;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.19
 */
public class GenericBlobStoreApiResponse
    extends BlobStoreApiModel
{
  private String name;

  private String type;

  private long blobCount;

  private long totalSizeInBytes;

  private long availableSpaceInBytes;

  @SuppressWarnings("unused") // Required for ITs
  public GenericBlobStoreApiResponse()
  {
    super();
  }

  public GenericBlobStoreApiResponse(final BlobStore blobStore) {
    super(blobStore.getBlobStoreConfiguration());
    BlobStoreConfiguration configuration = blobStore.getBlobStoreConfiguration();

    if (blobStore.isStarted()) {
      BlobStoreMetrics metrics = blobStore.getMetrics();
      blobCount = metrics.getBlobCount();
      totalSizeInBytes = metrics.getTotalSize();
      availableSpaceInBytes = metrics.getAvailableSpace();
    }

    name = checkNotNull(configuration.getName());
    setType(checkNotNull(configuration.getType()));
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public long getBlobCount() {
    return blobCount;
  }

  public void setBlobCount(final long blobCount) {
    this.blobCount = blobCount;
  }

  public long getTotalSizeInBytes() {
    return totalSizeInBytes;
  }

  public void setTotalSizeInBytes(final long totalSizeInBytes) {
    this.totalSizeInBytes = totalSizeInBytes;
  }

  public long getAvailableSpaceInBytes() {
    return availableSpaceInBytes;
  }

  public void setAvailableSpaceInBytes(final long availableSpaceInBytes) {
    this.availableSpaceInBytes = availableSpaceInBytes;
  }
}
