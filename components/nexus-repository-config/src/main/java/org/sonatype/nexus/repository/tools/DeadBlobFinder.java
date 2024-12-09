package org.sonatype.nexus.repository.tools;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.Repository;

/**
 * Examines Asset metadata and confirms the sha1 of all referenced blobs. Reports on any instances where
 * Blob binary is missing or indicates a different sha1 than that stored in the DB.
 * @since 3.3
 */
public interface DeadBlobFinder<A>
{
  /**
   * Based on the db metadata, confirm that all Blobs exist and sha1 values match. Can optionally ignore any records
   * that don't have a blobRef, which is expected for NuGet search results.
   * @parem repository  The Repository to inspect
   */
  default List<DeadBlobResult<A>> find(@NotNull final Repository repository) {
    return find(repository, true);
  }

  /**
   * Based on the db metadata, confirm that all Blobs exist and sha1 values match. Can optionally ignore any records
   * that don't have a blobRef, which is expected for NuGet search results.
   * @parem repository  The Repository to inspect
   * @param ignoreMissingBlobRefs
   */
  List<DeadBlobResult<A>> find(@NotNull final Repository repository, boolean ignoreMissingBlobRefs);
}
