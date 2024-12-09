package org.sonatype.nexus.content.maven;

import java.io.IOException;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.maven.MavenMetadataRebuildFacet;

/**
 * @since 3.26
 */
@Facet.Exposed
public interface MavenMetadataRebuildContentFacet
    extends MavenMetadataRebuildFacet
{
  String METADATA_REBUILD_KEY = "forceRebuild";

  void maybeRebuildMavenMetadata(
      final String path,
      final boolean update,
      final boolean rebuildChecksums)
      throws IOException;
}
