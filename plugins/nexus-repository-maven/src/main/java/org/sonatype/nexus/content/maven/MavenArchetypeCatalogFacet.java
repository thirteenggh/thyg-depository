package org.sonatype.nexus.content.maven;

import java.io.IOException;

import org.sonatype.nexus.repository.Facet;

/**
 * Facet for rebuilding maven archetype catalog.
 *
 * @since 3.25
 */
@Facet.Exposed
public interface MavenArchetypeCatalogFacet
    extends Facet
{
  void rebuildArchetypeCatalog() throws IOException;
}
