package org.sonatype.nexus.logging.task;

/**
 * Expose task information for the per task logs. See the TaskConfiguration class.
 *
 * @since 3.5
 */
public interface TaskLogInfo
{
  String getId();

  String getTypeId();

  String getName();

  String getMessage();

  String toString();
}
