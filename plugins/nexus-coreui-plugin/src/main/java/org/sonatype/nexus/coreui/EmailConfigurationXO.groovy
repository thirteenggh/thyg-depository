package org.sonatype.nexus.coreui

import javax.validation.constraints.NotNull

import org.sonatype.nexus.validation.constraint.Hostname
import org.sonatype.nexus.validation.constraint.PortNumber

import groovy.transform.ToString
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank

/**
 * Email configuration exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class EmailConfigurationXO
{
  boolean enabled

  @Hostname
  @NotBlank
  String host

  @PortNumber
  @NotNull
  Integer port

  String username

  String password

  @Email
  @NotBlank
  String fromAddress

  String subjectPrefix

  // SSL/TLS options

  boolean startTlsEnabled

  boolean startTlsRequired

  boolean sslOnConnectEnabled

  boolean sslCheckServerIdentityEnabled

  boolean nexusTrustStoreEnabled

  // TODO: Advanced configuration:
  // socket connection timeout
  // socket timeout
}
