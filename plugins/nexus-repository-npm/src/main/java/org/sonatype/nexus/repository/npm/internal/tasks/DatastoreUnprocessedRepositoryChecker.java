package org.sonatype.nexus.repository.npm.internal.tasks;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.npm.internal.tasks.ReindexNpmRepositoryManager.UnprocessedRepositoryChecker;

/**
 * @since 3.27
 */
@Named
@Singleton
public class DatastoreUnprocessedRepositoryChecker
    implements UnprocessedRepositoryChecker
{
  @Override
  public boolean isUnprocessedNpmRepository(final Repository repository) {
    // TODO
    return false;
  }
}
