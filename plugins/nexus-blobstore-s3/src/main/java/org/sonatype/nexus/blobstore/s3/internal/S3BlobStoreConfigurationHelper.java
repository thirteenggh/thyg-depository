package org.sonatype.nexus.blobstore.s3.internal;

import java.util.Optional;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import com.google.common.base.Predicates;
import com.google.common.base.Strings;

import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.BUCKET_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.BUCKET_PREFIX_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.CONFIG_KEY;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.DEFAULT_EXPIRATION_IN_DAYS;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.EXPIRATION_KEY;

/**
 * Helper for retrieving S3 specific settings from a {@link BlobStoreConfiguration}.
 *
 * @since 3.16
 */
public class S3BlobStoreConfigurationHelper
{
  private S3BlobStoreConfigurationHelper() {
    // empty
  }

  public static void setConfiguredBucket(final BlobStoreConfiguration blobStoreConfiguration, final String bucket) {
    blobStoreConfiguration.attributes(CONFIG_KEY).set(BUCKET_KEY, bucket);
  }

  public static String getConfiguredBucket(final BlobStoreConfiguration blobStoreConfiguration) {
    return blobStoreConfiguration.attributes(CONFIG_KEY).require(BUCKET_KEY).toString();
  }

  public static int getConfiguredExpirationInDays(final BlobStoreConfiguration blobStoreConfiguration) {
    return Integer.parseInt(
        blobStoreConfiguration.attributes(CONFIG_KEY).get(EXPIRATION_KEY, DEFAULT_EXPIRATION_IN_DAYS).toString()
    );
  }

  public static String getBucketPrefix(final BlobStoreConfiguration blobStoreConfiguration) {
    return Optional.ofNullable(blobStoreConfiguration.attributes(CONFIG_KEY).get(BUCKET_PREFIX_KEY, String.class))
        .filter(Predicates.not(Strings::isNullOrEmpty))
        .map(s -> s.replaceFirst("/$", "") + "/")
        .orElse("");
  }
}
