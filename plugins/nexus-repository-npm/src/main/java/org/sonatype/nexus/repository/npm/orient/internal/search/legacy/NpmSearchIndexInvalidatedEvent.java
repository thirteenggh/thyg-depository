package org.sonatype.nexus.repository.npm.orient.internal.search.legacy;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.npm.internal.search.legacy.NpmSearchIndexFacet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * npm search index invalidation event, fired by npm repositories when {@link NpmSearchIndexFacet#invalidateCachedSearchIndex()}
 * is invoked. Used to propagate the invalidation event to groups where repository with invalidated index cache is
 * member.
 *
 * @since 3.0
 * @deprecated No longer actively used by npm upstream, replaced by v1 search api (NEXUS-13150).
 */
@Deprecated
public class NpmSearchIndexInvalidatedEvent
{
  private final Repository repository;

  public NpmSearchIndexInvalidatedEvent(final Repository repository) {
    this.repository = checkNotNull(repository);
  }

  /**
   * The npm repository who's cached npm index was invalidated.
   */
  @Nonnull
  public Repository getRepository() {
    return repository;
  }
}
