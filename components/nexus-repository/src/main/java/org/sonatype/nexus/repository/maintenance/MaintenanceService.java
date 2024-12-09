package org.sonatype.nexus.repository.maintenance;

import java.util.Set;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

/**
 * A service for executing maintenance operations (such as a 'delete') on assets in a repository.
 *
 * @since 3.3
 */
public interface MaintenanceService
{
  /**
   * Delete an asset in the specified repository.
   * @return Set of asset names that were removed
   */
  Set<String> deleteAsset(Repository repository, Asset asset);

  /**
   * Delete a component in the specified repository.
   * @return Set of asset names that were removed
   */
  Set<String> deleteComponent(Repository repository, Component component);

  /**
   * Delete a path in the specified repository.
   *
   * @since 3.15
   */
  void deleteFolder(Repository repository, String path);

  /**
   * Check if a component can be deleted.
   * @return true if the component can be deleted, false otherwise
   *
   * @since 3.15
   */
  boolean canDeleteComponent(Repository repository, Component component);

  /**
   * Check if an asset can be deleted.
   * @return true if the asset can be deleted, false otherwise
   *
   * @since 3.15
   */
  boolean canDeleteAsset(Repository repository, Asset asset);

  /**
   * Check if user can potentially delete any asset in a given path.
   * @return true if the path can be deleted, false otherwise
   *
   * @since 3.15
   */
  boolean canDeleteFolder(Repository repository, String path);
}
