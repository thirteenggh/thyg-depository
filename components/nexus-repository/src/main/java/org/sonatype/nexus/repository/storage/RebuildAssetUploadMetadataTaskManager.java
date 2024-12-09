package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.TaskScheduler;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;

/**
 * @since 3.6
 */
@Named
@ManagedLifecycle(phase = TASKS)
@Singleton
public class RebuildAssetUploadMetadataTaskManager
    extends LifecycleSupport
{
  private final TaskScheduler taskScheduler;

  @Inject
  public RebuildAssetUploadMetadataTaskManager(final TaskScheduler taskScheduler) {
    this.taskScheduler = checkNotNull(taskScheduler);
  }

  @Override
  protected void doStart() {
    removeOldRebuildTasks();
  }

  private void removeOldRebuildTasks() {
    taskScheduler.listsTasks().stream()
        .filter(task -> RebuildAssetUploadMetadataTaskDescriptor.TYPE_ID.equals(task.getConfiguration().getTypeId()))
        .forEach(TaskInfo::remove);
  }
}
