package org.sonatype.nexus.scheduling;

/**
 * Alert conditions for task completion.
 *
 * @since 3.22
 */
public enum TaskNotificationCondition
{
  FAILURE,
  SUCCESS_FAILURE;

  public static final TaskNotificationCondition DEFAULT = FAILURE;
}
