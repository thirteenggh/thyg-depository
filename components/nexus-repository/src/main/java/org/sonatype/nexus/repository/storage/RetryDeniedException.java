package org.sonatype.nexus.repository.storage;

/**
 * Thrown when a storage operation fails and is not allowed to retry.
 *
 * @since 3.0
 */
public class RetryDeniedException
    extends org.sonatype.nexus.transaction.RetryDeniedException
{
  public RetryDeniedException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
