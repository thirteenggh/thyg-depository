package org.sonatype.nexus.common.time;

import org.joda.time.DateTime;

/**
 * A provider of the current time.
 *
 * @since 3.0
 */
public class Clock
{
  /**
   * Current time in milli-seconds.
   */
  public long millis(){
    return System.currentTimeMillis();
  }

  /**
   * Current time in nano-seconds.
   */
  public long nanos() {
    return System.nanoTime();
  }

  /**
   * Current date-time.
   */
  public DateTime dateTime(){
    return new DateTime(millis());
  }
}
