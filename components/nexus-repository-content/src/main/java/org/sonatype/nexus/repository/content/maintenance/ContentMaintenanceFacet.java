package org.sonatype.nexus.repository.content.maintenance;

import java.util.Set;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;

/**
 * @since 3.24
 */
@Facet.Exposed
public interface ContentMaintenanceFacet
    extends Facet
{
  /**
   * Deletes a component from the repository; the format may decide to trigger additional deletes/updates.
   *
   * @return paths(s) of deleted asset(s)
   */
  Set<String> deleteComponent(Component component);

  /**
   * Deletes an asset from the repository; the format may decide to trigger additional deletes/updates.
   *
   * @return path(s) of deleted asset(s)
   */
  Set<String> deleteAsset(Asset asset);

  /**
   * Delete a batch of components.
   *
   * @param componentIds  the componentIds to delete
   * @return number of components purged
   *
   * @since 3.29
   */
  int deleteComponents(int[] componentIds);
}
