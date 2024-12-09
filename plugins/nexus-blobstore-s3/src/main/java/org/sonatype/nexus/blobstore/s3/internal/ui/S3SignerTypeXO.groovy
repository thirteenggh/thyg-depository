package org.sonatype.nexus.blobstore.s3.internal.ui

import groovy.transform.ToString

/**
 * S3 signer type exchange object.
 *
 * @since 3.12
 */
@ToString(includePackage = false, includeNames = true)
class S3SignerTypeXO
{
  int order
  String id
  String name
}
