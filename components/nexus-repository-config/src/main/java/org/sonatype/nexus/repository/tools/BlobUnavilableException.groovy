package org.sonatype.nexus.repository.tools

/**
 * Thrown when Blob InputStream reports no available bytes.
 * @since 3.3
 */
class BlobUnavilableException
    extends Exception
{
  BlobUnavilableException() {
    super('Blob InputStream reports unavailable')
  }
}
