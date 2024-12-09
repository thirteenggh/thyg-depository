package org.sonatype.nexus.pax.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Mapped from {@link NexusLayoutEncoder}
 *
 * @since 3.6.1
 */
public class NexusNodeNameConverter
    extends ClassicConverter
{

  @Override
  public String convert(final ILoggingEvent iLoggingEvent) {
    return System.getProperty("nexus.clustered.nodeName", "");
  }
}
