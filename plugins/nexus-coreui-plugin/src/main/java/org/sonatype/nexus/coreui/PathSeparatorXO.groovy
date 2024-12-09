package org.sonatype.nexus.coreui

import org.hibernate.validator.constraints.NotEmpty

/**
 * @since 3.0
 */
class PathSeparatorXO
{
  @NotEmpty
  String path

  @NotEmpty
  String fileSeparator
}
