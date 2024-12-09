package org.sonatype.nexus.cleanup.storage

import groovy.transform.ToString
import groovy.transform.builder.Builder

/**
 * Cleanup Policy Preview exchange object.
 *
 * @since 3.14
 */
@Builder
@ToString(includePackage = false, includeNames = true)
class CleanupPolicyPreviewXO
{
  String repositoryName;

  CleanupPolicyCriteria criteria;
}
