package org.sonatype.nexus.repository.content.webhooks

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.audit.InitiatorProvider
import org.sonatype.nexus.common.app.FeatureFlag
import org.sonatype.nexus.common.entity.EntityId
import org.sonatype.nexus.common.node.NodeAccess
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.content.Component
import org.sonatype.nexus.repository.content.event.component.ComponentCreatedEvent
import org.sonatype.nexus.repository.content.event.component.ComponentDeletedEvent
import org.sonatype.nexus.repository.content.event.component.ComponentEvent
import org.sonatype.nexus.repository.content.event.component.ComponentPurgedEvent
import org.sonatype.nexus.repository.content.event.component.ComponentUpdatedEvent
import org.sonatype.nexus.repository.content.store.InternalIds
import org.sonatype.nexus.repository.rest.api.RepositoryItemIDXO
import org.sonatype.nexus.repository.webhooks.RepositoryWebhook
import org.sonatype.nexus.webhooks.Webhook
import org.sonatype.nexus.webhooks.WebhookPayload
import org.sonatype.nexus.webhooks.WebhookRequest

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.Subscribe

import static com.google.common.base.Preconditions.checkNotNull

/**
 * Repository {@link Component} {@link Webhook}.
 *
 * @since 3.27
 */
@FeatureFlag(name = "nexus.datastore.enabled")
@Named
@Singleton
class RepositoryComponentWebhook
    extends RepositoryWebhook
{
  public static final String NAME = "component"

  private NodeAccess nodeAccess

  private InitiatorProvider initiatorProvider

  @Override
  String getName() {
    return NAME
  }

  @Inject
  RepositoryComponentWebhook(final NodeAccess nodeAccess, final InitiatorProvider initiatorProvider) {
    this.nodeAccess = checkNotNull(nodeAccess)
    this.initiatorProvider = checkNotNull(initiatorProvider)
  }

  private enum EventAction
  {
    CREATED,
    UPDATED,
    DELETED,
    PURGED
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final ComponentCreatedEvent event) {
    maybeQueue(event, EventAction.CREATED)
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final ComponentUpdatedEvent event) {
    maybeQueue(event, EventAction.UPDATED)
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final ComponentDeletedEvent event) {
    maybeQueue(event, EventAction.DELETED)
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final ComponentPurgedEvent event) {
    maybeQueue(getPayload(event, EventAction.PURGED))
  }

  /**
   * Maybe queue {@link WebhookRequest} for event matching subscriptions.
   */
  private void maybeQueue(final ComponentEvent event, final EventAction eventAction) {
    maybeQueue(getPayload(event, eventAction))
  }

  /**
   * Maybe queue {@link WebhookRequest} for event matching subscriptions.
   */
  private void maybeQueue(final RepositoryComponentWebhookPayload payload) {
    subscriptions.each {
      def configuration = it.configuration as RepositoryWebhook.Configuration
      if (configuration.repository == payload.repositoryName) {
        queue(it, payload)
      }
    }
  }

  private RepositoryComponentWebhookPayload getPayload(final Repository repository, final EventAction eventAction) {
    new RepositoryComponentWebhookPayload(
        nodeId: nodeAccess.getId(),
        timestamp: new Date(),
        initiator: initiatorProvider.get(),
        repositoryName: repository.name,
        action: eventAction
    )
  }

  private RepositoryComponentWebhookPayload getPayload(final ComponentEvent event, final EventAction eventAction) {
    Repository repository = event.repository
    Component component = event.component
    EntityId componentId = InternalIds.toExternalId(InternalIds.internalComponentId(component))
    def payload = getPayload(repository, eventAction)
    payload.component = new RepositoryComponentWebhookPayload.RepositoryComponent(
        id: componentId.value,
        componentId: new RepositoryItemIDXO(repository.name, componentId.value).value,
        format: repository.format,
        name: component.name(),
        group: component.namespace(),
        version: component.version()
    )
    return payload
  }

  private RepositoryComponentWebhookPayload getPayload(
      final ComponentPurgedEvent event,
      final EventAction eventAction)
  {
    def payload = getPayload(event.repository, eventAction)
    payload.components = event.componentIds.collect {InternalIds.toExternalId(it).value }
    return payload
  }

  static class RepositoryComponentWebhookPayload
      extends WebhookPayload
  {
    String repositoryName

    EventAction action

    RepositoryComponent component

    String[] components

    static class RepositoryComponent
    {
      String id

      String componentId

      String format

      String name

      String group

      String version
    }
  }
}
