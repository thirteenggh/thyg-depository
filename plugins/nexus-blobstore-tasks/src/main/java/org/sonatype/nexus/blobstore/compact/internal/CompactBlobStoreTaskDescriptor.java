package org.sonatype.nexus.blobstore.compact.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.formfields.ComboboxFormField;
import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

import static org.sonatype.nexus.formfields.FormField.MANDATORY;

/**
 * Task descriptor for {@link CompactBlobStoreTask}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class CompactBlobStoreTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "blobstore.compact";

  public static final String BLOB_STORE_NAME_FIELD_ID = "blobstoreName";

  @Inject
  public CompactBlobStoreTaskDescriptor() {
    super(TYPE_ID,
        CompactBlobStoreTask.class,
        "Admin - Compact blob store",
        VISIBLE,
        EXPOSED,
        new ComboboxFormField<String>(
            BLOB_STORE_NAME_FIELD_ID,
            "Blob store",
            "Select the blob store to compact",
            MANDATORY
        ).withStoreApi("coreui_Blobstore.read").withIdMapping("name")
    );
  }

  @Override
  public void initializeConfiguration(final TaskConfiguration configuration) {
    configuration.setBoolean(MULTINODE_KEY, true);
  }
}
