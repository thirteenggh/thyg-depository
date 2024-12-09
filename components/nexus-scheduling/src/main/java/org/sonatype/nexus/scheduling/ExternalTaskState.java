package org.sonatype.nexus.scheduling;

import java.util.Date;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

/**
 * A simplified representation of a task's state suitable for external consumption.
 *
 * @since 3.20
 */
public class ExternalTaskState {
  private final TaskState state;

  private final TaskState lastEndState;

  private final Date lastRunStarted;

  private final Long lastRunDuration;

  public ExternalTaskState(final TaskState state,
                           @Nullable final TaskState lastEndState,
                           @Nullable final Date lastRunStarted,
                           @Nullable final Long lastRunDuration)
  {
    this.state = checkNotNull(state);
    this.lastEndState = lastEndState;
    this.lastRunStarted = lastRunStarted; // NOSONAR
    this.lastRunDuration = lastRunDuration;
  }

  public ExternalTaskState(final TaskInfo taskInfo) {
    this(
        taskInfo.getCurrentState().getState(),
        ofNullable(taskInfo.getLastRunState()).map(LastRunState::getEndState).orElse(null),
        ofNullable(taskInfo.getLastRunState()).map(LastRunState::getRunStarted).orElse(null),
        ofNullable(taskInfo.getLastRunState()).map(LastRunState::getRunDuration).orElse(null)
    );
  }

  public TaskState getState() {
    return state;
  }

  @Nullable
  public TaskState getLastEndState() {
    return lastEndState;
  }

  @Nullable
  public Date getLastRunStarted() {
    return lastRunStarted; // NOSONAR
  }

  @Nullable
  public Long getLastRunDuration() {
    return lastRunDuration;
  }
}
