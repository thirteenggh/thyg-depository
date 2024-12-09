package org.sonatype.nexus.repository.rest.api;

/**
 * @since 3.20
 */
public class IncompatibleRepositoryException extends Exception
{
  public IncompatibleRepositoryException(final String message) {
    super(message);
  }
}
