package org.sonatype.nexus.coreui.internal.atlas

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.common.atlas.SystemInformationGenerator
import org.sonatype.nexus.extdirect.DirectComponent
import org.sonatype.nexus.extdirect.DirectComponentSupport

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod
import org.apache.shiro.authz.annotation.RequiresPermissions

/**
 * System Information {@link DirectComponent}.
 *
 * @since 3.0
 */
@Named
@Singleton
@DirectAction(action = 'atlas_SystemInformation')
class SystemInformationComponent
    extends DirectComponentSupport
{
  @Inject
  SystemInformationGenerator systemInformationGenerator

  /**
   * Retrieves system information.
   *
   * @return a tree-structured report of critical system information details
   */
  @DirectMethod
  @Timed
  @ExceptionMetered
  @RequiresPermissions('nexus:atlas:read')
  Map read() {
    return systemInformationGenerator.report()
  }
}
