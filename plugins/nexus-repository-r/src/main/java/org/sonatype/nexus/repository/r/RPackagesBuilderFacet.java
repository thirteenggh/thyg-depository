package org.sonatype.nexus.repository.r;

import org.sonatype.nexus.repository.Facet;

/**
 * Facet implementing behavior for generating R PACKAGES metadata (not the actual R packages themselves). A typical
 * implementation will listen for changes to a repo and then rebuild the associated metadata.
 * <p>
 * TODO: Update this to support other PACKAGES files (not just PACKAGES.gz) when this work is done across the project.
 *
 * @since 3.28
 */
@Facet.Exposed
public interface RPackagesBuilderFacet
    extends Facet
{
  /**
   * Invalidates the metadata for this particular repository's PACKAGES.gz file at the specified path.
   *
   * @param basePath The base path of the PACKAGES.gz file to invalidate.
   */
  void invalidateMetadata(String basePath);
}
