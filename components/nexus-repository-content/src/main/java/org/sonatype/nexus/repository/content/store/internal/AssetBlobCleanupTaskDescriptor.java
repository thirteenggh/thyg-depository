package org.sonatype.nexus.repository.content.store.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * @since 3.24
 */
@Named
@Singleton
public class AssetBlobCleanupTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "assetBlob.cleanup";

  public static final String FORMAT_FIELD_ID = "format";

  public static final String CONTENT_STORE_FIELD_ID = "contentStore";

  public AssetBlobCleanupTaskDescriptor() {
    super(TYPE_ID,
        AssetBlobCleanupTask.class,
        "Admin - Cleanup unused asset blobs",
        VISIBLE,
        NOT_EXPOSED);
  }
}
