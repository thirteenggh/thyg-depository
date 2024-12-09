package org.sonatype.nexus.blobstore.s3.internal;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.blobstore.AccumulatingBlobStoreMetrics;
import org.sonatype.nexus.blobstore.BlobStoreMetricsNotAvailableException;
import org.sonatype.nexus.blobstore.BlobStoreMetricsStoreSupport;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaService;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.scheduling.PeriodicJobService;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A S3 specific {@link BlobStoreMetricsStoreSupport} implementation that retains blobstore metrics in memory,
 * periodically writing them out to S3.
 *
 * @since 3.6.1
 */
@Named
public class S3BlobStoreMetricsStore
    extends BlobStoreMetricsStoreSupport<S3PropertiesFile>
{

  static final ImmutableMap<String, Long> AVAILABLE_SPACE_BY_FILE_STORE = ImmutableMap.of("s3", Long.MAX_VALUE);

  private String bucket;

  private String bucketPrefix;

  private AmazonS3 s3;

  @Inject
  public S3BlobStoreMetricsStore(final PeriodicJobService jobService,
                                 final NodeAccess nodeAccess,
                                 final BlobStoreQuotaService quotaService,
                                 @Named("${nexus.blobstore.quota.warnIntervalSeconds:-60}")
                                 final int quotaCheckInterval)
  {
    super(nodeAccess, jobService, quotaService, quotaCheckInterval);
  }

  @Override
  protected S3PropertiesFile getProperties() {
    return new S3PropertiesFile(s3, bucket, bucketPrefix + nodeAccess.getId() + "-" + METRICS_FILENAME);
  }

  @Override
  protected AccumulatingBlobStoreMetrics getAccumulatingBlobStoreMetrics() {
    return new AccumulatingBlobStoreMetrics(0, 0, AVAILABLE_SPACE_BY_FILE_STORE, true);
  }

  @Override
  protected Stream<S3PropertiesFile> backingFiles() throws BlobStoreMetricsNotAvailableException {
    try {
      if (s3 == null) {
        return Stream.empty();
      }
      else {
        return s3.listObjects(bucket, bucketPrefix + nodeAccess.getId()).getObjectSummaries()
            .stream()
            .filter(Objects::nonNull)
            .filter(summary -> summary.getKey().endsWith(METRICS_FILENAME))
            .map(summary -> new S3PropertiesFile(s3, bucket, summary.getKey()));
      }
    }
    catch (SdkClientException e) {
      throw new BlobStoreMetricsNotAvailableException(e);
    }
  }

  public void setBucket(final String bucket) {
    checkNotNull(bucket);
    this.bucket = bucket;
  }

  public void setS3(final AmazonS3 s3) {
    checkNotNull(s3);
    this.s3 = s3;
  }

  public void setBucketPrefix(String bucketPrefix) {
    checkNotNull(bucketPrefix);
    this.bucketPrefix = bucketPrefix;
  }

  @Override
  public void remove() {
    try {
      backingFiles().forEach(this::removeQuietly);
    }
    catch (BlobStoreMetricsNotAvailableException e) {
      log.warn("Unable to remove metrics files", e);
    }
  }

  private void removeQuietly(final S3PropertiesFile file) {
    try {
      log.debug("Removing {}", file);
      file.remove();
    }
    catch (IOException e) {
      log.warn("Unable to remove metrics file {}", file, e);
    }
  }
}
