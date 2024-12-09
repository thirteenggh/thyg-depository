package org.sonatype.nexus.rapture.internal.logging;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.gossip.Level;
import org.sonatype.nexus.extdirect.DirectComponentSupport;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;
import com.softwarementors.extjs.djn.config.annotations.DirectAction;
import com.softwarementors.extjs.djn.config.annotations.DirectMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * LogEvent component.
 *
 * @since 3.0
 */
@Named
@Singleton
@DirectAction(action = "rapture_LogEvent")
public class LogEventComponent
    extends DirectComponentSupport
{
  private final boolean enabled;

  @Inject
  public LogEventComponent(@Named("${nexus.log.extdirect.recording.enabled:-false}") final boolean enabled) {
    this.enabled = enabled;
  }

  private static final Map<String, Level> levels = ImmutableMap.of(
      "trace", Level.TRACE,
      "debug", Level.DEBUG,
      "info", Level.INFO,
      "warn", Level.WARN,
      "error", Level.ERROR
  );

  @DirectMethod
  @Timed
  @ExceptionMetered
  public void recordEvent(final LogEventXO event) {
    if (!enabled) {
      return;
    }

    checkNotNull(event);

    Level level = levels.get(event.getLevel());
    checkState(level != null, "Invalid level: %s", event.getLevel());

    Logger logger = LoggerFactory.getLogger(event.getLogger());
    level.log(logger, event.getMessage());
  }
}
