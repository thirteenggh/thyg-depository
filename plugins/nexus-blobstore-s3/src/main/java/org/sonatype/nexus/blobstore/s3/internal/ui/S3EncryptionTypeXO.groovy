package org.sonatype.nexus.blobstore.s3.internal.ui

import groovy.transform.ToString

/**
 * S3 Encryption exchange object.
 *
 * @since 3.19
 */
@ToString(includePackage = false, includeNames = true)
class S3EncryptionTypeXO
{
  int order
  String id
  String name
}
