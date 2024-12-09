package org.sonatype.nexus.common.log;

/**
 * Marks the log with a user-supplied message.
 * 
 * @since 3.1
 */
public interface LogMarker
{
  void markLog(String message);
}
