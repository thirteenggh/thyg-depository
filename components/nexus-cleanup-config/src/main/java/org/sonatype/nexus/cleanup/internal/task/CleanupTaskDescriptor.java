package org.sonatype.nexus.cleanup.internal.task;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link CleanupTask}.
 *
 * @since 3.14
 */
@Named
@Singleton
public class CleanupTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "repository.cleanup";

  public CleanupTaskDescriptor() {
    super(TYPE_ID,
        CleanupTask.class,
        "Admin - Cleanup repositories using their associated policies",
        VISIBLE,
        NOT_EXPOSED
    );
  }
}
