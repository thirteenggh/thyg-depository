package org.sonatype.nexus.repository.internal.blobstore;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaResult;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaService;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;

import static java.lang.String.format;

/**
 * Inform on the health of all BlobStores based on their configured soft quota.
 */
@Named("Blob存储区")
@Singleton
public class BlobStoreHealthCheck
    extends HealthCheck
{
  private final Provider<BlobStoreManager> blobStoreManagerProvider;

  private final Provider<BlobStoreQuotaService> quotaServiceProvider;

  @Inject
  public BlobStoreHealthCheck(final Provider<BlobStoreManager> blobStoreManagerProvider,
                              final Provider<BlobStoreQuotaService> quotaServiceProvider)
  {
    this.blobStoreManagerProvider = Preconditions.checkNotNull(blobStoreManagerProvider);
    this.quotaServiceProvider = Preconditions.checkNotNull(quotaServiceProvider);
  }

  @Override
  protected Result check() {
    Iterable<BlobStore> blobstoreItr = blobStoreManagerProvider.get().browse();
    final List<String> violationMessages = StreamSupport.stream(blobstoreItr.spliterator(), false)
        .map(blobStore -> quotaServiceProvider.get().checkQuota(blobStore))
        .filter(Objects::nonNull)
        .filter(BlobStoreQuotaResult::isViolation)
        .map(BlobStoreQuotaResult::getMessage)
        .collect(Collectors.toList());

    String message = format("%s/%s Blob存储区违反了其配额<br>%s", violationMessages.size(),
        Iterators.size(blobStoreManagerProvider.get().browse().iterator()),
        String.join("<br>", violationMessages));

    return violationMessages.isEmpty() ? Result.healthy(message): Result.unhealthy(message);
  }
}
