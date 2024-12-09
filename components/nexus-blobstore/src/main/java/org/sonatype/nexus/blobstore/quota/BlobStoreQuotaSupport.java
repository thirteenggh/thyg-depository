package org.sonatype.nexus.blobstore.quota;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.String.format;

/**
 * A base class for {@link BlobStoreQuota} that holds constants which map to config values in {@link
 * BlobStoreConfiguration}
 *
 * @since 3.14
 */
public abstract class BlobStoreQuotaSupport
    extends ComponentSupport
    implements BlobStoreQuota
{
  public static final String ROOT_KEY = "blobStoreQuotaConfig";

  public static final String TYPE_KEY = "quotaType";

  public static final String LIMIT_KEY = "quotaLimitBytes";

  public static Runnable createQuotaCheckJob(final BlobStore blobStore,
                                             final BlobStoreQuotaService quotaService,
                                             final Logger logger)
  {
    return () -> quotaCheckJob(blobStore, quotaService, logger);
  }

  @VisibleForTesting
  static void quotaCheckJob(final BlobStore blobStore, final BlobStoreQuotaService quotaService, final Logger logger) {
    try {
      BlobStoreQuotaResult result = quotaService.checkQuota(blobStore);
      if (result != null && result.isViolation()) {
        logger.warn(result.getMessage());
      }
    }
    catch (Exception e) {
      // Don't propagate, as this stops subsequent executions
      logger.error("Quota check exception for {}", blobStore.getBlobStoreConfiguration().getName(), e);
    }
  }

  /**
   * Gets the blob store's quota limit from the blob store's configuration.
   *
   * @return the quota's limit
   * @since 3.15
   */
  public static long getLimit(final BlobStoreConfiguration config) {
    Number limitObj = config.attributes(ROOT_KEY).get(LIMIT_KEY, Number.class);
    checkArgument(limitObj != null, "Limit not found in configuration");
    return limitObj.longValue();
  }

  /**
   * @return the quota's type
   * @since 3.19
   */
  public static String getType(final BlobStoreConfiguration config) {
    return config.attributes(ROOT_KEY).get(TYPE_KEY, String.class);
  }

  @Override
  public String toString() {
    return getDisplayName();
  }
}
