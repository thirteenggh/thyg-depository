package org.sonatype.nexus.repository.storage.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.manager.RepositoryMetadataUpdatedEvent;

import com.google.common.eventbus.Subscribe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Triggers {@link RepositoryMetadataUpdatedEvent}s in response to {@link BucketUpdatedEvent}s.
 *
 * @since 3.22
 */
@Named
@Singleton
public class BucketEventInspector
    extends ComponentSupport
    implements EventAware
{
  private final RepositoryManager repositoryManager;

  private final EventManager eventManager;

  @Inject
  public BucketEventInspector(final RepositoryManager repositoryManager, final EventManager eventManager) {
    this.repositoryManager = checkNotNull(repositoryManager);
    this.eventManager = checkNotNull(eventManager);
  }

  @Subscribe
  public void onBucketUpdated(final BucketUpdatedEvent event) {
    Repository repository = repositoryManager.get(event.getRepositoryName());
    if (repository != null) {
      eventManager.post(new RepositoryMetadataUpdatedEvent(repository));
    }
    else {
      log.debug("Not posting metadata update event for deleted repository {}", event.getRepositoryName());
    }
  }
}
