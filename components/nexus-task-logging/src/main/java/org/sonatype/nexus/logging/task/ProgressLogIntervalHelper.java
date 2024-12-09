package org.sonatype.nexus.logging.task;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.PROGRESS;

/**
 * Helper for logging progress messages, one per defined interval.
 */
public class ProgressLogIntervalHelper
  implements AutoCloseable
{
  private final Stopwatch elapsed;

  private final Stopwatch progress;

  private final Logger logger;

  private final int internal;

  public ProgressLogIntervalHelper(final Logger logger, int intervalInSeconds) {
    this.logger = checkNotNull(logger);
    this.internal = intervalInSeconds;

    this.elapsed = Stopwatch.createStarted();
    this.progress = Stopwatch.createStarted();
  }

  /**
   * Get elapsed time as a string so it can be included in logs
   */
  public String getElapsed() {
    return elapsed.toString();
  }

  /**
   * Log the message using the PROGRESS marker. Will only send the log message to logback once per interval, otherwise
   * will store the message in the task logger context
   */
  public void info(String message, Object... args) {
    if (hasIntervalElapsed()) {
      logger.info(PROGRESS, message, args);
    }
    else {
      TaskLoggerHelper.progress(logger, message, args);
    }
  }

  /**
   * Flush any pending progress messages so they are logged immediately. This should be called when the section of code
   * receiving progress logging is complete, otherwise a stale progress message might get logged out of sequence.
   *
   * @see TaskLogger#flush()
   */
  public void flush() {
    TaskLoggerHelper.flush();
  }

  private boolean hasIntervalElapsed() {
    boolean logProgress = progress.elapsed(TimeUnit.SECONDS) >= internal;
    if (logProgress) {
      progress.reset().start();
    }
    return logProgress;
  }

  @Override
  public void close() {
    this.flush();
  }
}
