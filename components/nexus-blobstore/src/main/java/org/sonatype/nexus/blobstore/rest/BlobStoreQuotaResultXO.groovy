package org.sonatype.nexus.blobstore.rest

import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaResult

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * @since 3.14
 */
@CompileStatic
@ToString
class BlobStoreQuotaResultXO
{
  boolean isViolation

  @NotEmpty
  String message

  @NotEmpty
  String blobStoreName

  static BlobStoreQuotaResultXO asQuotaXO(BlobStoreQuotaResult result) {
    return new BlobStoreQuotaResultXO(
        isViolation: result.violation,
        message: result.message,
        blobStoreName: result.blobStoreName
    )
  }

  static BlobStoreQuotaResultXO asNoQuotaXO(String blobStoreName) {
    return new BlobStoreQuotaResultXO(
        isViolation: false,
        message: "Blob store ${blobStoreName} has no quota",
        blobStoreName: blobStoreName
    )
  }
}
