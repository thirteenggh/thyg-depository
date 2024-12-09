package org.sonatype.nexus.testsuite.helpers;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;

import org.joda.time.DateTime;


public interface ComponentAssetTestHelper
{
  /**
   * Get the created time of the blob associated with the asset at the path in the given repository.
   *
   * @param repository the containing repository
   * @param path the path of the asset
   */
  DateTime getBlobCreatedTime(Repository repository, String path);

  /**
   * Get the created time of the the asset at the path in the given repository.
   *
   * @param repository the containing repository
   * @param path the path of the asset
   */
  DateTime getAssetCreatedTime(Repository repository, String path);

  /**
   * Get the updated time of the blob associated with the asset at the path in the given repository.
   *
   * @param repository the containing repository
   * @param path the path of the asset
   */
  DateTime getBlobUpdatedTime(Repository repository, String path);

  /**
   * Get the last downloaded time for a path in the given repository.
   */
  DateTime getLastDownloadedTime(Repository repository, String path);

  /**
   * Delete a component in a repository from the database.
   */
  void deleteComponent(Repository repository, String namespace, String name, String version);

  /**
   * Remove an asset from a repository using ComponentMaintenance or similar.
   */
  void removeAsset(final Repository repository, final String path);

  /**
   * Retrieve the paths for all assets in a repository.
   */
  List<String> findAssetPaths(final String repositoryName);

  /**
   * Verify that an asset at the given path exists for the specified repository.
   */
  boolean assetExists(Repository repository, String path);

  /**
   * Verify that an asset at the given path exists for the specified repository, and associated with a component.
   */
  boolean assetWithComponentExists(Repository repository, String path, String group, String name);

  /**
   * Verify that an asset at the given path exists for the specified repository, and associated with a component.
   */
  boolean assetWithComponentExists(Repository repository, String path, String group, String name, String version);

  /**
   * Verify that an asset at the given path exists for the specified repository, and is not associated with a component.
   */
  boolean assetWithoutComponentExists(Repository repository, String path);

  /**
   * Verify that a component with the given name and version exists for the specified repository.
   */
  boolean componentExists(Repository repository, String name);

  /**
   * Verify that a component with the given name and version exists for the specified repository.
   */
  boolean componentExists(Repository repository, String name, String version);

  /**
   * Verify that a component at the given namespace, name, and version exists for the specified repository.
   */
  boolean componentExists(Repository repository, String namespace, String name, String version);

  /**
   * Verify that a component with an asset that matches the path exists.
   */
  boolean componentExistsWithAssetPathMatching(Repository repository, Predicate<String> pathMatcher);

  /**
   * Retrieve content type for a path within the repository.
   */
  String contentTypeFor(final String repositoryName, final String path);

  /**
   * Count the number of assets in the given repository.
   */
  int countAssets(Repository repository);

  /**
   * Count the number of components in the given repository.
   */
  int countComponents(final Repository repository);

  /**
   * Retrieve the attributes for the asset at the given path
   */
  NestedAttributesMap attributes(Repository repository, String path);

  /**
   * Retrieve the attributes for the component
   */
  NestedAttributesMap componentAttributes(Repository repository, String namespace, String name, String version);

  /**
   * Retrieve the attributes for the component.
   *
   * NOTE: this is intended for formats which do not have versions (e.g. raw)
   */
  NestedAttributesMap componentAttributes(Repository repository, String namespace, String name);

  /**
   * Set the last downloaded time for all assets in a repository.
   */
  void setLastDownloadedTime(Repository repository, int minusSeconds);

  /**
   * Set the last downloaded time for any asset matching the pathMatcher in the given repository.
   */
  void setLastDownloadedTime(Repository repository, int minusSeconds, Predicate<String> pathMatcher);

  /**
   * Adjust {@code path} for differences between Orient and Datastore
   */
  String adjustedPath(final String path);

  /**
   * Read an asset from the specified repository.
   */
  Optional<InputStream> read(Repository repository, String path);

  /**
   * Delete the blob associated with the asset with the specified path.
   */
  void deleteAssetBlob(Repository repository, String assetPath);

  /**
   * Obtains a blob ref of an asset in the given repo with the specified path.
   */
  BlobRef getBlobRefOfAsset(Repository repository, String path);

  /**
   * Gets the id of the component associated with the specified asset and repository.
   */
  EntityId getComponentId(Repository repository, String assetPath);

  NestedAttributesMap getAttributes(Repository repository);

  void modifyAttributes(final Repository repository, String child1, final String child2, final int value);

  class AssetNotFoundException
      extends RuntimeException
  {
    AssetNotFoundException(final Repository repository, final String path) {
      super("Missing asset: " + path + " from repository: " + repository.getName());
    }
  }

  class ComponentNotFoundException
      extends RuntimeException
  {
    ComponentNotFoundException(
        final Repository repository,
        final String namespace,
        final String name,
        final String version)
    {
      super(String.format("Repository:%s namespace:%s name:%s version:%s", repository.getName(), namespace, name,
          version));
    }
  }
}
