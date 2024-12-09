package org.sonatype.nexus.pax.logging;

import ch.qos.logback.access.pattern.AccessConverter;
import ch.qos.logback.access.spi.IAccessEvent;

/**
 * Converter to include a thread ID
 *
 * @since 3.17
 */
public class NexusThreadConverter
    extends AccessConverter
{
  public String convert(IAccessEvent accessEvent) {
    return Thread.currentThread().getName();
  }
}

