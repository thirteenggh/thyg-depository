package org.sonatype.nexus.scheduling;

/**
 * Generates notification messages for tasks.
 *
 * @since 3.22
 */
public interface TaskNotificationMessageGenerator {

  String completed(TaskInfo taskInfo);

  String failed(TaskInfo taskInfo, Throwable cause);
}
