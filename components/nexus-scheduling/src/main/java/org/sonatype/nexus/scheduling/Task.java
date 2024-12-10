package org.sonatype.nexus.scheduling;

import java.util.concurrent.Callable;

public interface Task
    extends Callable<Object>
{
  /**
   * Returns the copy of the task configuration.
   *
   * Modifying this map has no effect on effective configuration of the task.
   */
  TaskConfiguration taskConfiguration();

  /**
   * Configures the task.
   *
   * @throws IllegalArgumentException if passed in configuration is invalid.
   */
  void configure(TaskConfiguration taskConfiguration);

  /**
   * Returns a unique ID of the task instance.
   *
   * Shorthand method for {@link TaskConfiguration#getId()}.
   */
  String getId();

  /**
   * Returns a descriptive name of the task instance, usually set by user.
   */
  String getName();

  /**
   * Returns short generated message of current task's instance work.
   *
   * This message is based on task configuration and same typed tasks might emit different messages depending
   * on configuration.
   */
  String getMessage();
}
