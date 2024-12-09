package org.sonatype.nexus.coreui

import javax.validation.constraints.Max
import javax.validation.constraints.Min

import org.sonatype.nexus.httpclient.config.NonProxyHosts
import org.sonatype.nexus.validation.constraint.Hostname
import org.sonatype.nexus.validation.constraint.PortNumber

import groovy.transform.ToString

/**
 * HTTP System Settings exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class HttpSettingsXO
{
  String userAgentSuffix

  /**
   * Timeout seconds.
   */
  @Min(1L)
  @Max(3600L) // 1 hour
  Integer timeout

  @Min(0L)
  @Max(10L)
  Integer retries

  // HTTP proxy

  Boolean httpEnabled

  @Hostname
  String httpHost

  @PortNumber
  Integer httpPort

  // HTTP proxy-authentication

  Boolean httpAuthEnabled

  String httpAuthUsername

  String httpAuthPassword

  String httpAuthNtlmHost

  String httpAuthNtlmDomain

  // HTTPS proxy

  Boolean httpsEnabled

  @Hostname
  String httpsHost

  @PortNumber
  Integer httpsPort

  // HTTPS proxy-authentication

  Boolean httpsAuthEnabled

  String httpsAuthUsername

  String httpsAuthPassword

  String httpsAuthNtlmHost

  String httpsAuthNtlmDomain

  // HTTP[S] non-proxy hosts

  @NonProxyHosts
  Set<String> nonProxyHosts
}
