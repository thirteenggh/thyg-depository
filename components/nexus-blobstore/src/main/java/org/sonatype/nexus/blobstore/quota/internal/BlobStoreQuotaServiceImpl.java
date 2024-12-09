package org.sonatype.nexus.blobstore.quota.internal;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.ValidationException;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuota;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaResult;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaService;
import org.sonatype.nexus.rest.ValidationErrorsException;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.ROOT_KEY;
import static org.sonatype.nexus.blobstore.quota.BlobStoreQuotaSupport.TYPE_KEY;

/**
 * Default implementation of {@link BlobStoreQuotaService}
 *
 * @since 3.14
 */
@Named
@Singleton
public class BlobStoreQuotaServiceImpl
    extends ComponentSupport
    implements BlobStoreQuotaService
{
  private final Map<String, BlobStoreQuota> quotas;

  @Inject
  public BlobStoreQuotaServiceImpl(final Map<String, BlobStoreQuota> quotas) {
    this.quotas = checkNotNull(quotas);
  }

  @Override
  public void validateSoftQuotaConfig(final BlobStoreConfiguration config) {
    getQuotaType(config).ifPresent(type -> {
      if(!quotas.containsKey(type)) {
        throw new ValidationErrorsException("To enable Soft Quota, you must select a Type of Quota");
      }
    });
    getQuota(config).ifPresent(quota -> quota.validateConfig(config));
  }

  private Optional<String> getQuotaType(final BlobStoreConfiguration config) {
    return ofNullable(config.attributes(ROOT_KEY).get(TYPE_KEY, String.class));
  }

  private Optional<BlobStoreQuota> getQuota(final BlobStoreConfiguration config) {
    Optional<String> quotaType = getQuotaType(config);
    Optional<BlobStoreQuota> quota = quotaType.map(quotas::get);

    if (quotaType.isPresent() && !quota.isPresent()) {
      log.error("For blob store {} unable to find quota type for key {}", config.getName(), quotaType.get());
    }
    return quota;
  }

  @Nullable
  @Override
  public BlobStoreQuotaResult checkQuota(final BlobStore blobStore) {
    checkNotNull(blobStore);
    BlobStoreConfiguration config = blobStore.getBlobStoreConfiguration();

    return getQuota(config).map(quota -> {
      log.debug("Checking blob store {} for quota {}", config.getName(), quota);
      return quota.check(blobStore);
    }).orElse(null);
  }
}
