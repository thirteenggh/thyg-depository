package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


/**
 * @since 3.6
 */
@Named
@Singleton
public class RebuildAssetUploadMetadataConfiguration
{
  private final boolean enabled;

  private final int pageSize;

  @Inject
  public RebuildAssetUploadMetadataConfiguration(@Named("${nexus.asset.rebuildUploadMetadata.enabled:-true}") final boolean enabled,
                                                 @Named("${nexus.asset.rebuildUploadMetadata.pageSize:-1000}") final int pageSize) {
    this.enabled = enabled;
    this.pageSize = pageSize;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public int getPageSize() {
    return pageSize;
  }
}
