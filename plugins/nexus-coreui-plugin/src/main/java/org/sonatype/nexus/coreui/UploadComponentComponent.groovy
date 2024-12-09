package org.sonatype.nexus.coreui

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.coreui.internal.UploadService
import org.sonatype.nexus.extdirect.DirectComponentSupport
import org.sonatype.nexus.rapture.StateContributor
import org.sonatype.nexus.repository.upload.UploadConfiguration
import org.sonatype.nexus.repository.upload.UploadDefinition

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod

/**
 * @since 3.7
 */
@Named
@Singleton
@DirectAction(action = 'coreui_Upload')
class UploadComponentComponent
    extends DirectComponentSupport
    implements StateContributor
{

  @Inject
  UploadService uploadService

  @Inject
  UploadConfiguration configuration

  @DirectMethod
  @Timed
  @ExceptionMetered
  Collection<UploadDefinition> getUploadDefinitions() {
    return uploadService.getAvailableDefinitions().findAll{ definition -> definition.isUiUpload() }
  }

  @Override
  @Nullable
  Map<String, Object> getState() {
    return ['upload': configuration.enabled]
  }
}
