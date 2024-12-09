package org.sonatype.nexus.repository.browse;

import java.util.List;
import java.util.Map;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.query.PageResult;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.security.RepositorySelector;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import com.orientechnologies.orient.core.id.ORID;

/**
 * Consolidates code for user browsing of repositories, specifically for the user interface.
 *
 * @since 3.1
 */
public interface BrowseService
{
  /**
   * Returns a {@link PageResult} for browsing the specified repository and query options.
   */
  PageResult<Component> browseComponents(final Repository repository,
                                           final QueryOptions queryOptions);

  /**
   * Returns a {@link PageResult} of assets for the specified component. Note that the Repository passed in is not
   * necessarily the Repository where the component resides (in the case of a group Repository).
   */
  PageResult<Asset> browseComponentAssets(final Repository repository, final String componentId);

  /**
   * Returns a {@link PageResult} of assets for the specified component. Note that the Repository passed in is not
   * necessarily the Repository where the component resides (in the case of a group Repository).
   *
   * @since 3.14
   */
  PageResult<Asset> browseComponentAssets(final Repository repository, final Component component);

  /**
   * Returns a {@link PageResult} of assets based on the specified information.
   */
  PageResult<Asset> browseAssets(final Repository repository,
                                   final QueryOptions queryOptions);

  /**
   * Returns a {@link PageResult} for previewing the specified repository based on an arbitrary content selector.
   */
  PageResult<Asset> previewAssets(final RepositorySelector selectedRepository,
                                    final List<Repository> repositories,
                                    final String jexlExpression,
                                    final QueryOptions queryOptions);

  /**
   * Returns an asset based on the supplied id and repository.
   */
  Asset getAssetById(ORID assetId, final Repository repository);

  /**
   * Returns an asset based on the supplied id and repository.
   * 
   * @since 3.6.1
   */
  Asset getAssetById(EntityId assetId, final Repository repository);

  /**
   * Returns a component based on the supplied id and repository.
   */
  Component getComponentById(final ORID componentId, final Repository repository);

  /**
   * Returns a map of bucket IDs to repository names for any buckets that could be referenced by the repository.
   */
  Map<EntityId, String> getRepositoryBucketNames(final Repository repository);
}
