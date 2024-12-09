package org.sonatype.nexus.repository.content.facet;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.ContentRepository;
import org.sonatype.nexus.repository.content.fluent.FluentAssets;
import org.sonatype.nexus.repository.content.fluent.FluentAttributes;
import org.sonatype.nexus.repository.content.fluent.FluentBlobs;
import org.sonatype.nexus.repository.content.fluent.FluentComponents;

/**
 * Repository {@link Facet} for content related metadata.
 *
 * @since 3.21
 */
@Facet.Exposed
public interface ContentFacet
    extends Facet, ContentRepository, FluentAttributes<ContentFacet>
{
  /**
   * Ingest blobs.
   */
  FluentBlobs blobs();

  /**
   * Manage components.
   */
  FluentComponents components();

  /**
   * Manage assets.
   */
  FluentAssets assets();
}
