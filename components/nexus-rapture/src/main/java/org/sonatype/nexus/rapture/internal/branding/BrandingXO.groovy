package org.sonatype.nexus.rapture.internal.branding

import groovy.transform.ToString

/**
 * Branding exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class BrandingXO
{
  Boolean headerEnabled

  String headerHtml

  Boolean footerEnabled

  String footerHtml
}
