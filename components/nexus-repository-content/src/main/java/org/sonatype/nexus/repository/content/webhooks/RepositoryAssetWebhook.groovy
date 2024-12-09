package org.sonatype.nexus.repository.content.webhooks

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.audit.InitiatorProvider
import org.sonatype.nexus.common.app.FeatureFlag
import org.sonatype.nexus.common.entity.EntityId
import org.sonatype.nexus.common.node.NodeAccess
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.content.Asset
import org.sonatype.nexus.repository.content.event.asset.AssetCreatedEvent
import org.sonatype.nexus.repository.content.event.asset.AssetDeletedEvent
import org.sonatype.nexus.repository.content.event.asset.AssetEvent
import org.sonatype.nexus.repository.content.event.asset.AssetPurgedEvent
import org.sonatype.nexus.repository.content.event.asset.AssetUpdatedEvent
import org.sonatype.nexus.repository.content.store.InternalIds
import org.sonatype.nexus.repository.rest.api.RepositoryItemIDXO
import org.sonatype.nexus.repository.webhooks.RepositoryWebhook
import org.sonatype.nexus.webhooks.Webhook
import org.sonatype.nexus.webhooks.WebhookPayload
import org.sonatype.nexus.webhooks.WebhookRequest

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.Subscribe

/**
 * Repository {@link Asset} {@link Webhook}.
 *
 * @since 3.27
 */
@FeatureFlag(name = "nexus.datastore.enabled")
@Named
@Singleton
class RepositoryAssetWebhook
    extends RepositoryWebhook
{
  public static final String NAME = 'asset'

  @Inject
  NodeAccess nodeAccess

  @Inject
  InitiatorProvider initiatorProvider

  @Override
  String getName() {
    return NAME
  }

  private static enum EventAction
  {
    CREATED,
    UPDATED,
    DELETED,
    PURGED
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final AssetCreatedEvent event) {
    maybeQueue(event, EventAction.CREATED)
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final AssetUpdatedEvent event) {
    maybeQueue(event, EventAction.UPDATED)
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final AssetDeletedEvent event) {
    maybeQueue(event, EventAction.DELETED)
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final AssetPurgedEvent event) {
    maybeQueue(getPayload(event, EventAction.PURGED))
  }

  /**
   * Maybe queue {@link WebhookRequest} for event matching subscriptions.
   */
  private void maybeQueue(final AssetEvent event, final EventAction eventAction) {
    maybeQueue(getPayload(event, eventAction))
  }

  /**
   * Maybe queue {@link WebhookRequest} for event matching subscriptions.
   */
  private void maybeQueue(final RepositoryAssetWebhookPayload payload) {
    subscriptions.each {
      def configuration = it.configuration as RepositoryWebhook.Configuration
      if (configuration.repository == payload.repositoryName) {
        queue(it, payload)
      }
    }
  }

  private RepositoryAssetWebhookPayload getPayload(final Repository repository, final EventAction eventAction) {
    new RepositoryAssetWebhookPayload(
        nodeId: nodeAccess.getId(),
        timestamp: new Date(),
        initiator: initiatorProvider.get(),
        repositoryName: repository.name,
        action: eventAction
    )
  }

  private RepositoryAssetWebhookPayload getPayload(final AssetEvent event, final EventAction eventAction) {
    Repository repository = event.repository
    Asset asset = event.asset
    EntityId assetId = InternalIds.toExternalId(InternalIds.internalAssetId(asset))
    def payload = getPayload(repository, eventAction)
    payload.asset = new RepositoryAssetWebhookPayload.RepositoryAsset(
        id: assetId.value,
        assetId: new RepositoryItemIDXO(repository.name, assetId.value).value,
        format: repository.format,
        name: asset.path()
    )
    return payload
  }

  private RepositoryAssetWebhookPayload getPayload(final AssetPurgedEvent event, final EventAction eventAction) {
    def payload = getPayload(event.repository, eventAction)
    payload.assets = event.assetIds.collect {InternalIds.toExternalId(it).value }
    return payload
  }

  static class RepositoryAssetWebhookPayload
      extends WebhookPayload
  {
    String repositoryName

    EventAction action

    RepositoryAsset asset

    String[] assets

    static class RepositoryAsset
    {
      String id

      String assetId

      String format

      String name
    }
  }
}
