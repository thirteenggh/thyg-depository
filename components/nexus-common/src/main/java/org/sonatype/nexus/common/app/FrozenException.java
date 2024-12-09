package org.sonatype.nexus.common.app;

/**
 * Thrown when something attempts to write while the application is frozen.
 *
 * @since 3.21
 */
public class FrozenException
    extends NotWritableException
{
  private static final long serialVersionUID = -5328665935242655134L;

  public FrozenException(final String message) {
    super(message);
  }

  public FrozenException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
