package org.sonatype.nexus.internal.commands

import javax.inject.Inject
import javax.inject.Named

import org.sonatype.nexus.common.app.ApplicationLicense
import org.sonatype.nexus.common.app.ApplicationVersion

import org.apache.karaf.shell.api.action.Action
import org.apache.karaf.shell.api.action.Command

import static org.apache.karaf.shell.support.ansi.SimpleAnsi.INTENSITY_BOLD
import static org.apache.karaf.shell.support.ansi.SimpleAnsi.INTENSITY_NORMAL

/**
 * Display Nexus system information.
 *
 * @since 3.0
 */
@Named
@Command(name = 'info', scope = 'nexus', description = 'Nexus system information')
class InfoAction
    implements Action
{
  @Inject
  ApplicationVersion applicationVersion

  @Inject
  ApplicationLicense applicationLicense

  @Override
  public Object execute() throws Exception {
    def section = { String name ->
      println INTENSITY_BOLD + name + INTENSITY_NORMAL
    }
    def entry = { String key, Object value ->
      println "  $key: $value"
    }
    def sep = {
      println()
    }

    section 'Application'
    entry 'Version', applicationVersion.version
    entry 'Edition', applicationVersion.edition
    sep()

    // build information
    section 'Build'
    entry 'Revision', applicationVersion.buildRevision
    entry 'Timestamp', applicationVersion.buildTimestamp
    sep()

    // license information
    section 'License'
    entry 'Valid', applicationLicense.valid
    entry 'Required', applicationLicense.required
    entry 'Installed', applicationLicense.installed
    entry 'Expired', applicationLicense.expired
    entry 'Fingerprint', applicationLicense.fingerprint

    if (!applicationLicense.attributes.isEmpty()) {
      sep()
      section 'License Attributes'
      applicationLicense.attributes.each { key, value ->
        entry key, value
      }
    }

    return null
  }
}
