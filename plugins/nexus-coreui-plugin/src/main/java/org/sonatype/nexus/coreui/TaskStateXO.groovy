package org.sonatype.nexus.coreui

import groovy.transform.ToString

/**
 * Exchange object for per-node state of a task.
 *
 * @since 3.1
 */
@ToString(includePackage = false, includeNames = true)
class TaskStateXO
{
  String status
  String statusDescription

  String lastRunResult

  String nodeId
}
