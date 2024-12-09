package org.sonatype.nexus.internal.datastore.task;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.formfields.ComboboxFormField;
import org.sonatype.nexus.formfields.StringTextFormField;
import org.sonatype.nexus.scheduling.TaskDescriptor;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

import static org.sonatype.nexus.formfields.FormField.MANDATORY;

/**
 * A {@link TaskDescriptor} for backing up an embedded H2 datastore.
 *
 * @since 3.21
 */
@Named
@Singleton
public class H2BackupTaskDescriptor
    extends TaskDescriptorSupport
{
  static final String TYPE_ID = "h2.backup.task";

  static final String LOCATION = "location";

  static final String DATASTORE = "dataStoreName";

  public H2BackupTaskDescriptor()
  {
    super(TYPE_ID, H2BackupTask.class, "Admin - Backup H2 Database", VISIBLE, EXPOSED, false,
        new ComboboxFormField<String>(
            DATASTORE,
            "Datastore",
            "The datastore to backup",
            MANDATORY
        ).withStoreApi("coreui_Datastore.readH2").withIdMapping("name").withNameMapping("name"),
        new StringTextFormField(LOCATION, "Location",
            "The full path for the database backup zip. May use {datetime} to have the present time included",
            MANDATORY));
  }
}
