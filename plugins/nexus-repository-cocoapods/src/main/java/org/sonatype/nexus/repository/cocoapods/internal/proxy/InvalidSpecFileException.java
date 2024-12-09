package org.sonatype.nexus.repository.cocoapods.internal.proxy;

/**
 * @since 3.19
 */
public class InvalidSpecFileException
  extends Exception
{
  public InvalidSpecFileException(final String reason) {
    super(reason);
  }

  public InvalidSpecFileException(final String reason, Throwable throwable) {
    super(reason, throwable);
  }
}
