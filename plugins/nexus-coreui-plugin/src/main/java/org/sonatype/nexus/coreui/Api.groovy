package org.sonatype.nexus.coreui

import com.softwarementors.extjs.djn.config.annotations.DirectAction
import org.sonatype.nexus.rapture.StateContributor

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * API feature flag
 *
 * @since 3.6
 */
@Named
@Singleton
@DirectAction(action = 'coreui_Api')
class Api
    implements StateContributor
{
  @Inject
  @Named('${nexus.admin.system.apidocs.enabled:-true}')
  boolean enabled

  @Override
  @Nullable
  Map<String, Object> getState() {
    return ['api': enabled]
  }
}
