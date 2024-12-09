package org.sonatype.nexus.blobstore.compact.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.api.BlobStoreUsageChecker;
import org.sonatype.nexus.scheduling.Cancelable;
import org.sonatype.nexus.scheduling.TaskSupport;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.blobstore.compact.internal.CompactBlobStoreTaskDescriptor.BLOB_STORE_NAME_FIELD_ID;

/**
 * Task to compact a given blob store.
 *
 * @since 3.0
 */
@Named
public class CompactBlobStoreTask
    extends TaskSupport
    implements Cancelable
{
  private final BlobStoreManager blobStoreManager;

  private final BlobStoreUsageChecker blobStoreUsageChecker;

  @Inject
  public CompactBlobStoreTask(final BlobStoreManager blobStoreManager,
                              final BlobStoreUsageChecker blobStoreUsageChecker)
  {
    this.blobStoreManager = checkNotNull(blobStoreManager);
    this.blobStoreUsageChecker = checkNotNull(blobStoreUsageChecker);
  }

  @Override
  protected Object execute() throws Exception {
    BlobStore blobStore = blobStoreManager.get(getBlobStoreField());
    if (blobStore != null) {
      blobStore.compact(blobStoreUsageChecker);
    }
    else {
      log.warn("Unable to find blob store: {}", getBlobStoreField());
    }
    return null;
  }

  @Override
  public String getMessage() {
    return "Compacting " + getBlobStoreField() + " blob store";
  }

  private String getBlobStoreField() {
    return getConfiguration().getString(BLOB_STORE_NAME_FIELD_ID);
  }
}
