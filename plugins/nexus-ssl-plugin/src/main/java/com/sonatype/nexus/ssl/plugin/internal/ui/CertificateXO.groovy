package com.sonatype.nexus.ssl.plugin.internal.ui

import groovy.transform.ToString

/**
 * Certificate exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
public class CertificateXO
{
  String id
  String fingerprint
  String pem
  String serialNumber
  String subjectCommonName
  String subjectOrganization
  String subjectOrganizationalUnit
  String issuerCommonName
  String issuerOrganization
  String issuerOrganizationalUnit
  long issuedOn
  long expiresOn
  boolean inTrustStore
}
