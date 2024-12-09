package org.sonatype.nexus.repository.r.internal;

/**
 * RException now with more R Exceptions
 *
 * @since 3.28
 */
public class RException
    extends RuntimeException
{
  private String url;

  public RException(final String url, final Throwable cause) {
    super(cause);
    this.url = url;
  }

  @Override
  public String getMessage() {
    return String.format("Error requesting %s", url);
  }
}
