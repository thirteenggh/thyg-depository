package org.sonatype.nexus.blobstore.quota.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuota;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaResult;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport;
import org.sonatype.nexus.rest.ValidationErrorsException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.text.UnitFormatter.formatStorage;
import static java.lang.String.format;

/**
 * A {@link BlobStoreQuota} which checks that a blob store has at least a certain amount of space left.
 *
 * @since 3.14
 */
@Named(SpaceRemainingQuota.ID)
@Singleton
public class SpaceRemainingQuota
    extends BlobStoreQuotaSupport
{
  public static final String ID = "spaceRemainingQuota";

  private static final String DISPLAY_NAME = "Space Remaining";

  @Override
  public void validateConfig(final BlobStoreConfiguration config) {
    if (getLimit(config) <= 0) {
      throw new ValidationErrorsException(DISPLAY_NAME + " quotas must have a Quota Limit greater than 0");
    }
  }

  @Override
  public BlobStoreQuotaResult check(final BlobStore blobStore) {
    checkNotNull(blobStore);

    long availableSpace = blobStore.getMetrics().getAvailableSpace();
    boolean isUnlimited = blobStore.getMetrics().isUnlimited();
    long limit = getLimit(blobStore.getBlobStoreConfiguration());

    String name = blobStore.getBlobStoreConfiguration().getName();
    String msg = format("Blob store %s is limited to having %s available space, and has %s space remaining",
        name,
        formatStorage(limit),
        formatStorage(availableSpace));

    return new BlobStoreQuotaResult(!isUnlimited && availableSpace < limit, name, msg);
  }

  @Override
  public String getDisplayName() {
    return DISPLAY_NAME;
  }

  @Override
  public String getId() {
    return ID;
  }
}
