package org.sonatype.nexus.coreui.internal.node

import groovy.transform.ToString

/**
 * For transmitting info about nodes in cluster
 *
 * @since 3.2
 */
@ToString(includePackage = false, includeNames = true)
class NodeInfoXO
{
  String name
  Boolean local
  String displayName
}
