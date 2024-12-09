package org.sonatype.nexus.coreui

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.extdirect.DirectComponent
import org.sonatype.nexus.extdirect.DirectComponentSupport
import org.sonatype.nexus.repository.webhooks.RepositoryWebhook
import org.sonatype.nexus.webhooks.GlobalWebhook
import org.sonatype.nexus.webhooks.WebhookService
import org.sonatype.nexus.webhooks.WebhookType

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod
import org.apache.shiro.authz.annotation.RequiresPermissions

/**
 * Webhook {@link DirectComponent}.
 *
 * @since 3.1
 */
@Named
@Singleton
@DirectAction(action = 'coreui_Webhook')
class WebhookComponent
    extends DirectComponentSupport
{
  @Inject
  WebhookService webhookService

  private List<ReferenceXO> findWebhooksWithType(final WebhookType type) {
    return webhookService.webhooks.findAll {
      it.type == type
    }
    .collect {
      new ReferenceXO(id: it.name, name: it.name)
    }
  }

  /**
   * Returns all {@link GlobalWebhook} instances.
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresPermissions('nexus:settings:read')
  List<ReferenceXO> listWithTypeGlobal() {
    return findWebhooksWithType(GlobalWebhook.TYPE)
  }

  /**
   * Returns all {@link RepositoryWebhook} instances.
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresPermissions('nexus:settings:read')
  List<ReferenceXO> listWithTypeRepository() {
    return findWebhooksWithType(RepositoryWebhook.TYPE)
  }
}
