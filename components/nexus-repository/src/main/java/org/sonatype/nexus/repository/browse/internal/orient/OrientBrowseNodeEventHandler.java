package org.sonatype.nexus.repository.browse.internal.orient;

import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.HasLocality;
import org.sonatype.nexus.repository.config.ConfigurationDeletedEvent;
import org.sonatype.nexus.repository.storage.AssetCreatedEvent;
import org.sonatype.nexus.repository.storage.AssetDeletedEvent;
import org.sonatype.nexus.repository.storage.AssetUpdatedEvent;
import org.sonatype.nexus.repository.storage.ComponentDeletedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.orient.ReplicationModeOverrides.clearReplicationModeOverrides;
import static org.sonatype.nexus.orient.ReplicationModeOverrides.dontWaitForReplicationResults;

/**
 * Listens to any events that require managing folder data and calls the format-specific handler.
 *
 * @since 3.6
 */
@Singleton
@Named
public class OrientBrowseNodeEventHandler
    implements EventAware, EventAware.Asynchronous
{
  private final OrientBrowseNodeManager browseNodeManager;

  @Inject
  public OrientBrowseNodeEventHandler(final OrientBrowseNodeManager browseNodeManager) {
    this.browseNodeManager = checkNotNull(browseNodeManager);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final AssetCreatedEvent event) {
    handle(event, e -> browseNodeManager.createFromAsset(e.getRepositoryName(), e.getAsset()));
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final AssetUpdatedEvent event) {
    handle(event, e -> browseNodeManager.maybeCreateFromUpdatedAsset(e.getRepositoryName(), e.getAssetId(), e.getAsset()));
  }

  @Subscribe
  public void on(final AssetDeletedEvent event) {
    handle(event, e -> browseNodeManager.deleteAssetNode(e.getAsset()));
  }

  @Subscribe
  public void on(final ComponentDeletedEvent event) {
    handle(event, e -> browseNodeManager.deleteComponentNode(e.getComponent()));
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final ConfigurationDeletedEvent event) {
    handle(event, e -> browseNodeManager.deleteByRepository(e.getRepositoryName()));
  }

  private <E extends HasLocality> void handle(final E event, final Consumer<E> consumer) {
    checkNotNull(event);
    if (event.isLocal()) {
      dontWaitForReplicationResults();
      try {
        consumer.accept(event);
      }
      finally {
        clearReplicationModeOverrides();
      }
    }
  }
}
