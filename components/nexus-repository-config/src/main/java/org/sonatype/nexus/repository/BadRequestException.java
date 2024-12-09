package org.sonatype.nexus.repository;

/**
 * @since 3.3
 */
public class BadRequestException
    extends RuntimeException
{
  public BadRequestException(final String message) {
    super(message);
  }
}
