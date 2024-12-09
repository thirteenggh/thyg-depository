package org.sonatype.nexus.scheduling;

import java.util.Date;

/**
 * Encapsulates the end state of the last run of a task.
 *
 * @since 3.19
 */
public interface LastRunState
{
  /**
   * Returns the last end state.
   */
  TaskState getEndState();

  /**
   * Returns the date of last run start.
   */
  Date getRunStarted();

  /**
   * Returns the last run duration.
   */
  long getRunDuration();
}
