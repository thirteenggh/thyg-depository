package org.sonatype.nexus.repository.r.internal.hosted;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Event dispatched when R metadata (PACKAGES files) should be invalidated and rebuilt.
 *
 * @since 3.28
 */
public class RMetadataInvalidationEvent
{
  /**
   * The repository name for the metadata invalidation.
   */
  private final String repositoryName;

  /**
   * The base path (without filename) to invalidate the PACKAGES content for.
   */
  private final String basePath;

  /**
   * Constructor
   *
   * @param repositoryName The repository name for the metadata invalidation.
   * @param basePath       The base path (without filename) of the path to invalidate the PACKAGES content for.
   */
  public RMetadataInvalidationEvent(final String repositoryName, final String basePath) {
    this.repositoryName = checkNotNull(repositoryName);
    this.basePath = checkNotNull(basePath);
  }

  /**
   * Returns the repository name associated with the metadata invalidation request.
   *
   * @return the repository name for the invalidation
   */
  public String getRepositoryName() {
    return repositoryName;
  }

  /**
   * Returns the base path (without filename) to invalidate the PACKAGES content for.
   *
   * @return the base path for the invalidation
   */
  public String getBasePath() {
    return basePath;
  }
}
