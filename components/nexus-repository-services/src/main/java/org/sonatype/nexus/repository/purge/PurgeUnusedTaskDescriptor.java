package org.sonatype.nexus.repository.purge;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.NumberTextFormField;
import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

import static org.sonatype.nexus.repository.purge.PurgeUnusedTask.LAST_USED_FIELD_ID;
import static org.sonatype.nexus.repository.purge.PurgeUnusedTask.REPOSITORY_NAME_FIELD_ID;

/**
 * Task descriptor for {@link PurgeUnusedTask}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class PurgeUnusedTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TASK_NAME = "Repository - Delete unused components";

  public static final String TYPE_ID = "repository.purge-unused";

  public static final Number LAST_USED_INIT_VALUE = 1;

  public static final Number LAST_USED_MIN_VALUE = 1;

  public PurgeUnusedTaskDescriptor() {
    super(TYPE_ID,
        PurgeUnusedTask.class,
        TASK_NAME,
        VISIBLE,
        EXPOSED,
        new RepositoryCombobox(
            REPOSITORY_NAME_FIELD_ID,
            "Repository",
            "Select the repository to purge components/assets from",
            FormField.MANDATORY
        ).includingAnyOfFacets(PurgeUnusedFacet.class).includeAnEntryForAllRepositories(),
        new NumberTextFormField(
            LAST_USED_FIELD_ID,
            "Last used in days",
            "Purge all components and assets that were last used before given number of days",
            FormField.MANDATORY
        ).withInitialValue(LAST_USED_INIT_VALUE).withMinimumValue(LAST_USED_MIN_VALUE)
    );
  }
}
