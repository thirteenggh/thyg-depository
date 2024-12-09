package org.sonatype.nexus.repository.r.internal;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.BucketStore;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentDirector;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.28
 */
@Named(RFormat.NAME)
@Singleton
public class RComponentDirector
    extends ComponentSupport
    implements ComponentDirector
{
  private final BucketStore bucketStore;

  private final RepositoryManager repositoryManager;

  @Inject
  public RComponentDirector(final BucketStore bucketStore,
                            final RepositoryManager repositoryManager)
  {
    this.bucketStore = checkNotNull(bucketStore);
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Override
  public boolean allowMoveTo(final Repository destination) {
    return true;
  }

  @Override
  public boolean allowMoveTo(final Component component, final Repository destination) {
    return repositoryFor(component).isPresent();
  }

  @Override
  public boolean allowMoveFrom(final Repository source) {
    return true;
  }

  private Optional<Repository> repositoryFor(final Component component) {
    return Optional.of(component)
        .map(Component::bucketId)
        .map(bucketStore::getById)
        .map(Bucket::getRepositoryName)
        .map(repositoryManager::get);
  }
}
