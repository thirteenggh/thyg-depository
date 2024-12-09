package org.sonatype.nexus.coreui

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.validation.Valid
import javax.validation.constraints.NotNull

import org.sonatype.nexus.common.app.FreezeService
import org.sonatype.nexus.extdirect.DirectComponentSupport
import org.sonatype.nexus.validation.Validate

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.apache.shiro.authz.annotation.RequiresPermissions

/**
 * Component for freezing and releasing the application.
 *
 * @since 3.2
 */
@Named
@Singleton
@DirectAction(action = 'coreui_Freeze')
class FreezeComponent
    extends DirectComponentSupport
{

  @Inject
  FreezeService freezeService

  @DirectMethod
  @Timed
  @ExceptionMetered
  FreezeStatusXO read() {
    return buildStatus()
  }

  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresAuthentication
  @RequiresPermissions("nexus:*")
  @Validate
  FreezeStatusXO update(final @NotNull @Valid FreezeStatusXO freezeStatusXO) {
    if (freezeStatusXO.frozen) {
      freezeService.requestFreeze('UI request')
    }
    else {
      freezeService.cancelFreeze()
    }
    return buildStatus()
  }

  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresAuthentication
  @RequiresPermissions("nexus:*")
  @Validate
  FreezeStatusXO forceRelease() {
    freezeService.cancelAllFreezeRequests()
    return buildStatus()
  }

  private FreezeStatusXO buildStatus() {
    return new FreezeStatusXO(
        frozen: freezeService.isFrozen()
    )
  }
}
