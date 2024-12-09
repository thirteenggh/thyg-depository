package org.sonatype.nexus.content.example;

import java.io.IOException;
import java.util.Optional;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

/**
 * Provides persistent content for an 'example' format.
 *
 * @since 3.24
 */
@Facet.Exposed
public interface ExampleContentFacet
    extends ContentFacet
{
  Optional<Content> get(String path) throws IOException;

  Content put(String path, Payload content) throws IOException;

  boolean delete(String path) throws IOException;
}
