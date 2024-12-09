package org.sonatype.nexus.repository.proxy;

import com.google.common.collect.ListMultimap;

/**
 * @since 3.18.1
 */
public class BypassHttpErrorException
    extends RuntimeException
{
  private final int httpCode;

  private final String reason;

  private final ListMultimap<String, String> headers;

  public BypassHttpErrorException(final int httpCode, final String reason, final ListMultimap<String, String> headers) {
    this.httpCode = httpCode;
    this.reason = reason;
    this.headers = headers;
  }

  public int getStatusCode() {
    return httpCode;
  }

  public String getReason() {
    return reason;
  }

  public ListMultimap<String, String> getHeaders() {
    return headers;
  }
}
