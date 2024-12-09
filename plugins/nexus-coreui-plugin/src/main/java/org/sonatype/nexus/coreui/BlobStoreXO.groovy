package org.sonatype.nexus.coreui

import javax.validation.constraints.Size

import org.sonatype.nexus.validation.group.Create
import static org.sonatype.nexus.blobstore.BlobStoreSupport.MAX_NAME_LENGTH
import static org.sonatype.nexus.blobstore.BlobStoreSupport.MIN_NAME_LENGTH

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Range

/**
 * @since 3.0
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
class BlobStoreXO
{
  @NotEmpty
  @UniqueBlobStoreName(groups = Create)
  @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
  String name

  @NotEmpty
  String type

  String isQuotaEnabled

  String quotaType

  @Range
  Long quotaLimit

  @NotEmpty
  Map<String, Map<String, Object>> attributes

  @Range
  long blobCount

  @Range
  long totalSize

  @Range
  long availableSpace

  @Range
  long repositoryUseCount

  boolean unlimited

  /**
   * @since 3.19
   */
  boolean unavailable

  @Range
  long blobStoreUseCount

  boolean inUse

  boolean promotable

  /**
   * @since 3.29
   */
  int taskUseCount

  /**
   * the name of the group to which this blob store belongs, or null if not in a group
   * @since 3.15
   */
  String groupName
}
