package org.sonatype.repository.helm.internal.createindex;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Facet.Exposed;

/**
 * Facet interface for rebuilding Helm index.yaml files
 *
 * @since 3.28
 */
@Exposed
public interface CreateIndexFacet
    extends Facet
{
  /**
   * Mark the helm index yaml as invalidated such that it will be rebuilt after waiting for a configured amount of time
   * to prevent unnecessary successive rebuilds of the metadata.
   */
  void invalidateIndex();
}
