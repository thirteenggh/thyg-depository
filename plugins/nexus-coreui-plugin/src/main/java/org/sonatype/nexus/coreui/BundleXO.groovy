package org.sonatype.nexus.coreui

import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.Range

/**
 * OSGI bundle.
 *
 * @since 3.0
 */
class BundleXO
{
  @Range
  long id

  @NotBlank
  String state

  @NotBlank
  String name

  @NotBlank
  String symbolicName

  @NotBlank
  String location

  @NotBlank
  String version

  @Range
  int startLevel

  boolean fragment

  // TODO: expose as DateTime?
  long lastModified

  /**
   * Fragment bundle ids.
   */
  List<Long> fragments

  /**
   * Fragment-host bundle ids.
   */
  List<Long> fragmentHosts

  Map<String,String> headers
}
