package org.sonatype.nexus.blobstore;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaService;

public abstract class BlobStoreDescriptorSupport
    implements BlobStoreDescriptor
{

  private final BlobStoreQuotaService quotaService;

  public BlobStoreDescriptorSupport(final BlobStoreQuotaService quotaService) {
    this.quotaService = quotaService;
  }

  @Override
  public void validateConfig(final BlobStoreConfiguration configuration) {
    quotaService.validateSoftQuotaConfig(configuration);
  }
}
