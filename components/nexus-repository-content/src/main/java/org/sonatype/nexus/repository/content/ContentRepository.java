package org.sonatype.nexus.repository.content;

import org.sonatype.nexus.common.entity.EntityId;

/**
 * Top-level content metadata for the repository; distinct from the repository entity in the config store.
 *
 * @since 3.20
 */
public interface ContentRepository
    extends RepositoryContent
{
  /**
   * Identity of the associated repository entity in the config store.
   */
  EntityId configRepositoryId();

  /**
   * Identity of the associated repository entity in the content store.
   */
  Integer contentRepositoryId();
}
