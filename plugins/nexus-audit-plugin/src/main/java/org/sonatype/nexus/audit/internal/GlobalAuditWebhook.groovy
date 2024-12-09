package org.sonatype.nexus.audit.internal

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.audit.AuditDataRecordedEvent
import org.sonatype.nexus.webhooks.GlobalWebhook
import org.sonatype.nexus.webhooks.Webhook
import org.sonatype.nexus.webhooks.WebhookPayload

import com.google.common.eventbus.AllowConcurrentEvents
import com.google.common.eventbus.Subscribe

/**
 * Global audit {@link Webhook}.
 *
 * @since 3.1
 */
@Named
@Singleton
class GlobalAuditWebhook
    extends GlobalWebhook
{
  public static final String NAME = 'audit'

  @Override
  String getName() {
    return NAME
  }

  @Subscribe
  @AllowConcurrentEvents
  void on(final AuditDataRecordedEvent event) {

    def auditData = event.data
    def payload = new AuditWebhookPayload(
        initiator: auditData.initiator,
        nodeId: auditData.nodeId
    )

    payload.audit = new AuditWebhookPayload.Audit(
        domain: auditData.domain,
        context: auditData.context,
        type: auditData.type,
        attributes: auditData.attributes
    )

    subscriptions.each {
      queue(it, payload)
    }
  }

  static class AuditWebhookPayload
      extends WebhookPayload
  {
    Audit audit

    static class Audit
    {
      String domain

      String type

      String context

      Map<String, String> attributes
    }
  }
}

