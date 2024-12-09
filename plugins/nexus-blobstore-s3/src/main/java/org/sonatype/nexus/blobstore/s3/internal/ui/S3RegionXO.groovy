package org.sonatype.nexus.blobstore.s3.internal.ui

import groovy.transform.ToString

/**
 * S3 Region exchange object.
 *
 * @since 3.12
 */
@ToString(includePackage = false, includeNames = true)
class S3RegionXO
{
  int order
  String id
  String name
}
