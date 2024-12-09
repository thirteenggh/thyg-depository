package org.sonatype.nexus.internal.log;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.log.LogManager;
import org.sonatype.nexus.common.log.LogMarkInsertedEvent;
import org.sonatype.nexus.common.log.LogMarker;
import org.sonatype.nexus.common.log.LoggerLevel;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link LogMarker}.
 * 
 * @since 3.1
 */
@Named
@Singleton
public class LogMarkerImpl
    extends ComponentSupport
    implements LogMarker
{
  private final LogManager logManager;

  private final EventManager eventManager;

  @Inject
  public LogMarkerImpl(final LogManager logManager, final EventManager eventManager) {
    this.logManager = checkNotNull(logManager);
    this.eventManager = checkNotNull(eventManager);
  }

  @Override
  public void markLog(final String message) {
    // ensure that level for marking logger is enabled
    LoggerLevel loggerLevel = logManager.getLoggerEffectiveLevel(log.getName());
    if (LoggerLevel.INFO.compareTo(loggerLevel) < 0) {
      logManager.setLoggerLevel(log.getName(), LoggerLevel.INFO);
    }

    String asterixes = Strings.repeat("*", message.length() + 4);
    log.info("\n{}\n* {} *\n{}", asterixes, message, asterixes);

    eventManager.post(new LogMarkInsertedEvent(message));
  }
}
