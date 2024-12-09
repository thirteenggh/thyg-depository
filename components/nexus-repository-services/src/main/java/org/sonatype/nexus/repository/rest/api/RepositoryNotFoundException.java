package org.sonatype.nexus.repository.rest.api;

/**
 * @since 3.20
 */
public class RepositoryNotFoundException
    extends Exception
{
  private static final String MESSAGE = "Repository not found";

  public RepositoryNotFoundException() {
    super(MESSAGE);
  }
}
