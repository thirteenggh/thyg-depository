package org.sonatype.nexus.internal.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

import static com.google.common.base.Preconditions.checkArgument;

public class NexusConnectionKeepAliveStrategy
    extends DefaultConnectionKeepAliveStrategy
{
  // FIXME: Clean up names, used as max here, but config passed in isn't a "max"

  /**
   * The max duration for how long to pool a connection in milliseconds. Used as default too, instead of
   * "indefinite" case.
   */
  private final long maxKeepAliveDuration;

  /**
   * @param maxKeepAliveDuration the max duration in millis for how long to pool the connection.
   */
  NexusConnectionKeepAliveStrategy(final long maxKeepAliveDuration) {
    checkArgument(maxKeepAliveDuration > -1, "maxKeepAliveDuration must be 0 or higher, but is set to %s", maxKeepAliveDuration);
    this.maxKeepAliveDuration = maxKeepAliveDuration;
  }

  public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
    // ask super class
    final long result = super.getKeepAliveDuration(response, context);
    if (result < 0) {
      // if "indefinite", use default
      return maxKeepAliveDuration;
    }
    else {
      // else "cap" it with max
      return Math.min(result, maxKeepAliveDuration);
    }
  }
}
