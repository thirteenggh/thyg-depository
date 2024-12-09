package org.sonatype.nexus.repository.maven;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;

/**
 * @since 3.29
 */
@Facet.Exposed
public interface MavenMetadataRebuildFacet
    extends Facet
{
  /**
   * Rebuilds/updates Maven metadata. The parameters depend each on previous, and if any of those are set (ie. G, GA or
   * GAV), the metadata will be updated. Rebuild is possible only against repository as whole, not a sub-part of it.
   *
   * @param groupId     scope the work to given groupId.
   * @param artifactId  scope the work to given artifactId (groupId must be given).
   * @param baseVersion scope the work to given baseVersion (groupId and artifactId must be given).
   * @param rebuildChecksums  whether or not checksums should be checked and corrected if found
   *                           missing or incorrect
   */
  void rebuildMetadata(@Nullable String groupId,
                       @Nullable String artifactId,
                       @Nullable String baseVersion,
                       boolean rebuildChecksums);

  /**
   * Rebuilds/updates Maven metadata. The parameters depend each on previous, and if any of those are set (ie. G, GA or
   * GAV), the metadata will be updated. Rebuild is possible only against repository as whole, not a sub-part of it.
   *
   * @param groupId     scope the work to given groupId.
   * @param artifactId  scope the work to given artifactId (groupId must be given).
   * @param baseVersion scope the work to given baseVersion (groupId and artifactId must be given).
   * @param rebuildChecksums  whether or not checksums should be checked and corrected if found
   *                           missing or incorrect
   * @param update      whether to update or replace metadata
   *
   * @since 3.22
   */
  void rebuildMetadata(@Nullable String groupId,
                       @Nullable String artifactId,
                       @Nullable String baseVersion,
                       boolean rebuildChecksums,
                       boolean update);
}
