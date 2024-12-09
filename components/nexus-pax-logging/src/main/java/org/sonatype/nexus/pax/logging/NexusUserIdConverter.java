package org.sonatype.nexus.pax.logging;

import ch.qos.logback.access.pattern.AccessConverter;
import ch.qos.logback.access.spi.IAccessEvent;

/**
 * Converter for the request attribute named by {@link #ATTR_USER_ID}
 *
 * @since 3.0
 */
public class NexusUserIdConverter
    extends AccessConverter
{
  /**
   * "nexus.user.id" request attribute name
   *
   * @see org.sonatype.nexus.web.SecurityFilter
   */
  private static final String ATTR_USER_ID = "nexus.user.id";

  public String convert(IAccessEvent accessEvent) {
    return accessEvent.getAttribute(ATTR_USER_ID);
  }
}

