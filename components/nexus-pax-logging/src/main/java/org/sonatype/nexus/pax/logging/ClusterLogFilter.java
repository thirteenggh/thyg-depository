package org.sonatype.nexus.pax.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;

import static ch.qos.logback.core.spi.FilterReply.DENY;
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.CLUSTER_LOG_ONLY;

/**
 * Logback {@link Filter} for cluster_nexus.log
 * - Must have the CLUSTER_LOG_ONLY marker.
 *
 * @see org.ops4j.pax.logging.slf4j.Slf4jLogger#info(Marker, String)
 * @since 3.16
 */
public class ClusterLogFilter
    extends Filter<ILoggingEvent>
{
  @Override
  public FilterReply decide(final ILoggingEvent event) {
    return CLUSTER_LOG_ONLY.equals(event.getMarker()) ? NEUTRAL : DENY;
  }
}
