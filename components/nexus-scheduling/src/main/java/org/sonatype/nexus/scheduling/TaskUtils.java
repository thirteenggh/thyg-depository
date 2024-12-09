package org.sonatype.nexus.scheduling;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.29
 */
@Named
@Singleton
public class TaskUtils
{
  private final Provider<TaskScheduler> taskSchedulerProvider;

  @Inject
  public TaskUtils(final Provider<TaskScheduler> taskSchedulerProvider) {
    this.taskSchedulerProvider = checkNotNull(taskSchedulerProvider);
  }

  public void checkForConflictingTasks(
      final String taskId,
      final String taskName,
      final List<String> conflictingTypeIds,
      final Map<String, List<String>> conflictingConfiguration)
  {
    Set<TaskInfo> incompatibleTasks = taskSchedulerProvider.get().listsTasks().stream()
        .filter(taskInfo -> isConflictingTask(taskId, taskInfo, conflictingTypeIds, conflictingConfiguration))
        .collect(Collectors.toSet());

    String names = incompatibleTasks.stream().map(TaskInfo::getName).collect(Collectors.joining(","));

    if (!incompatibleTasks.isEmpty()) {
      throw new IllegalStateException(
          "Cannot start task '" + taskName + "' there is at least one other task (" + names +
              ") running that is conflicting, please restart this task once the other(s) complete.");
    }
  }

  private boolean isConflictingTask(
      final String currentTaskId,
      final TaskInfo taskInfo,
      final List<String> conflictingTypeIds,
      final Map<String, List<String>> conflictingConfiguration)
  {
    //ignore tasks that aren't in the conflicting type set
    if (!conflictingTypeIds.contains(taskInfo.getTypeId())) {
      return false;
    }

    //ignore 'this' task
    if (currentTaskId.equals(taskInfo.getId())) {
      return false;
    }

    //ignore tasks that aren't running
    if (!taskInfo.getCurrentState().getState().isRunning()) {
      return false;
    }

    //ignore tasks that aren't dealing with same config (i.e. don't conflict if 2 tasks dealing with diff blobstores)
    return conflictingConfiguration.entrySet().stream()
        .anyMatch(entry -> entry.getValue().contains(taskInfo.getConfiguration().getString(entry.getKey())));
  }
}
