package org.sonatype.nexus.repository.maven;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.sonatype.nexus.repository.Facet;

/**
 * Maven hosted facet, present on all Maven hosted-type repositories.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface MavenHostedFacet
    extends MavenMetadataRebuildFacet
{
  /**
   * Rebuilds archetype catalog for given repository. Returns the number of archetypes hosted.
   */
  int rebuildArchetypeCatalog() throws IOException;

  /**
   * Delete metadata associated with the Maven artifact, and rebuild metadata to account for the deletion.
   */
  Set<String> deleteMetadata(String groupId, String artifactId, String baseVersion);

  /**
   * Delete metadata associated with the Maven artifact, and rebuild metadata to account for the deletion.
   */
  void deleteMetadata(List<String[]> gavs);
}
