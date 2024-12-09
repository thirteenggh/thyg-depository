package org.sonatype.nexus.pax.logging;

import org.sonatype.nexus.logging.task.TaskLoggerHelper;
import org.sonatype.nexus.logging.task.TaskLoggingEvent;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;

import static ch.qos.logback.core.spi.FilterReply.DENY;
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL;
import static org.sonatype.nexus.logging.task.TaskLogger.LOGBACK_TASK_DISCRIMINATOR_ID;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.INTERNAL_PROGRESS;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.NEXUS_LOG_ONLY;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.PROGRESS;

/**
 * Logback {@link Filter} for task logs (see tasklogfile in logback.xml). Ensures that the task logs get the appropriate
 * entries:
 * - Thread must be executing in a task (determined by presence of discriminator in MDC)
 * - Must NOT have the NEXUS_LOG marker. This prevents double entry for the progress update to the nexus.log
 * - Also sets progress entries into the TaskLoggerHelper
 *
 * @since 3.5
 */
public class TaskLogsFilter
    extends Filter<ILoggingEvent>
{
  @Override
  public FilterReply decide(final ILoggingEvent event) {
    Marker marker = event.getMarker();

    if (PROGRESS.equals(marker)) {
      // store the progress value in the threadlocal
      TaskLoggerHelper.progress(toTaskLoggerEvent(event));
    }

    if (MDC.get(LOGBACK_TASK_DISCRIMINATOR_ID) == null) {
      // not executing in a task...
      return DENY;
    }

    if (NEXUS_LOG_ONLY.equals(marker) || INTERNAL_PROGRESS.equals(marker)) {
      // not meant for task log
      return DENY;
    }

    return NEUTRAL;
  }

  private TaskLoggingEvent toTaskLoggerEvent(final ILoggingEvent event) {
    Logger logger = LoggerFactory.getLogger(event.getLoggerName());
    return new TaskLoggingEvent(logger, event.getMessage(), event.getArgumentArray());
  }
}
