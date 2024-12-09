package org.sonatype.nexus.repository.content.store;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.content.AttributeChangeSet;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.event.asset.AssetAttributesEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetCreatedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetDeletedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetDownloadedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetKindEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetPreDeleteEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetPrePurgeEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetPurgedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetUploadedEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryDeletedEvent;
import org.sonatype.nexus.repository.content.fluent.internal.FluentAssetImpl;
import org.sonatype.nexus.transaction.Transactional;

import com.google.inject.assistedinject.Assisted;

import static java.util.Arrays.stream;
import static org.sonatype.nexus.repository.content.AttributesHelper.applyAttributeChange;

/**
 * {@link Asset} store.
 *
 * @since 3.21
 */
@Named
public class AssetStore<T extends AssetDAO>
    extends ContentStoreEventSupport<T>
{
  @Inject
  public AssetStore(final DataSessionSupplier sessionSupplier,
                    @Assisted final String contentStoreName,
                    @Assisted final Class<T> daoClass)
  {
    super(sessionSupplier, contentStoreName, daoClass);
  }

  /**
   * Count all assets in the given repository.
   *
   * @param repositoryId the repository to count
   * @param kind optional kind of assets to count
   * @param filter optional filter to apply
   * @param filterParams parameter map for the optional filter
   * @return count of assets in the repository
   */
  @Transactional
  public int countAssets(final int repositoryId,
                         @Nullable final String kind,
                         @Nullable final String filter,
                         @Nullable final Map<String, Object> filterParams)
  {
    return dao().countAssets(repositoryId, kind, filter, filterParams);
  }

  /**
   * Browse all assets in the given repository in a paged fashion.
   *
   * @param repositoryId the repository to browse
   * @param limit maximum number of assets to return
   * @param continuationToken optional token to continue from a previous request
   * @param kind optional kind of assets to return
   * @param filter optional filter to apply
   * @param filterParams parameter map for the optional filter
   * @return collection of assets and the next continuation token
   *
   * @see Continuation#nextContinuationToken()
   */
  @Transactional
  public Continuation<Asset> browseAssets(final int repositoryId,
                                          final int limit,
                                          @Nullable final String continuationToken,
                                          @Nullable final String kind,
                                          @Nullable final String filter,
                                          @Nullable final Map<String, Object> filterParams)
  {
    return dao().browseAssets(repositoryId, limit, continuationToken, kind, filter, filterParams);
  }

  /**
   * Browse all assets in the given repositories in a paged fashion. The returned assets will be sorted
   * by asset id in ascending order.
   *
   * @param repositoryIds     the repositories to browse
   * @param limit             maximum number of assets to return
   * @param continuationToken optional token to continue from a previous request
   * @return collection of assets and the next continuation token
   * @see Continuation#nextContinuationToken()
   *
   * @since 3.27
   */
  @Transactional
  public Continuation<Asset> browseAssets(
      final Set<Integer> repositoryIds,
      final int limit,
      @Nullable final String continuationToken)
  {
    return dao().browseAssetsInRepositories(repositoryIds, limit, continuationToken);
  }

  /**
   * Browse all assets associated with the given logical component.
   *
   * @param component the component to browse
   * @return collection of assets
   */
  @Transactional
  public Collection<Asset> browseComponentAssets(final Component component) {
    return dao().browseComponentAssets(component);
  }

  /**
   * Creates the given asset in the content data store.
   *
   * @param asset the asset to create
   */
  @Transactional
  public void createAsset(final AssetData asset) {
    dao().createAsset(asset);

    postCommitEvent(() -> new AssetCreatedEvent(asset));
  }

  /**
   * Retrieves an asset from the content data store.
   *
   * @param assetId the internalId of the asset
   * @return asset if it was found
   */
  @Transactional
  public Optional<Asset> readAsset(final int assetId) {
    return dao().readAsset(assetId);
  }

  /**
   * Retrieves an asset located at the given path in the content data store.
   *
   * @param repositoryId the repository containing the asset
   * @param path the path of the asset
   * @return asset if it was found
   */
  @Transactional
  public Optional<Asset> readPath(final int repositoryId, final String path) {
    return dao().readPath(repositoryId, path);
  }

  /**
   * Updates the kind of the given asset in the content data store.
   *
   * @param asset the asset to update
   *
   * @since 3.25
   */
  @Transactional
  public void updateAssetKind(final Asset asset) {
    dao().updateAssetKind(asset);

    postCommitEvent(() -> new AssetKindEvent(asset));
  }

  /**
   * Updates the attributes of the given asset in the content data store.
   *
   * @param asset the asset to update
   */
  @Transactional
  public void updateAssetAttributes(final Asset asset,
                                    final AttributeChangeSet changeSet)
  {
    // reload latest attributes, apply change, then update database if necessary
    dao().readAssetAttributes(asset).ifPresent(attributes -> {
      ((AssetData) asset).setAttributes(attributes);

      boolean changesApplied = changeSet.getChanges().stream()
          .map(change -> applyAttributeChange(attributes, change))
          .reduce((a, b) -> a || b)
          .orElse(false);
      if (changesApplied) {
        dao().updateAssetAttributes(asset);

        postCommitEvent(() -> new AssetAttributesEvent(asset, changeSet.getChanges()));
      }
    });
  }

  /**
   * Updates the link between the given asset and its {@link AssetBlob} in the content data store.
   *
   * @param asset the asset to update
   */
  @Transactional
  public void updateAssetBlobLink(final Asset asset) {
    dao().updateAssetBlobLink(asset);

    postCommitEvent(() -> new AssetUploadedEvent(asset));
  }

  /**
   * Updates the last downloaded time of the given asset in the content data store.
   *
   * @param asset the asset to update
   */
  @Transactional
  public void markAsDownloaded(final Asset asset) {
    dao().markAsDownloaded(asset);

    postCommitEvent(() -> new AssetDownloadedEvent(asset));
  }

  /**
   * Deletes an asset from the content data store.
   *
   * @param asset the asset to delete
   * @return {@code true} if the asset was deleted
   */
  @Transactional
  public boolean deleteAsset(final Asset asset) {
    preCommitEvent(() -> new AssetPreDeleteEvent(asset));
    postCommitEvent(() -> new AssetDeletedEvent(asset));

    return dao().deleteAsset(asset);
  }

  /**
   * Deletes the asset located at the given path in the content data store.
   *
   * @param repositoryId the repository containing the asset
   * @param path the path of the asset
   * @return {@code true} if the asset was deleted
   */
  @Transactional
  public boolean deletePath(final int repositoryId, final String path) {
    return dao().readPath(repositoryId, path)
        .map(this::deleteAsset)
        .orElse(false);
  }

  /**
   * Deletes the assets located at the given paths in the content data store.
   *
   * @param repositoryId the repository containing the assets
   * @param paths the paths of the assets to delete
   * @return {@code true} if any of the assets were deleted
   */
  @Transactional
  public boolean deleteAssetsByPaths(final int repositoryId, final List<String> paths) {
    return dao().deleteAssetsByPaths(repositoryId, paths);
  }

  /**
   * Deletes all assets in the given repository from the content data store.
   *
   * Events will not be sent for these deletes, instead listen for {@link ContentRepositoryDeletedEvent}.
   *
   * @param repositoryId the repository containing the assets
   * @return {@code true} if any assets were deleted
   */
  @Transactional
  public boolean deleteAssets(final int repositoryId) {
    log.debug("Deleting all assets in repository {}", repositoryId);
    boolean deleted = false;
    while (dao().deleteAssets(repositoryId, deleteBatchSize())) {
      commitChangesSoFar();
      deleted = true;
    }
    log.debug("Deleted all assets in repository {}", repositoryId);
    return deleted;
  }

  /**
   * Purge assets without a component in the given repository last downloaded more than given number of days ago
   *
   * @param repositoryId the repository to check
   * @param daysAgo the number of days ago to check
   * @return number of purged assets
   *
   * @since 3.24
   */
  @Transactional
  public int purgeNotRecentlyDownloaded(final int repositoryId, final int daysAgo) {
    int purged = 0;
    while (true) {
      int[] assetIds = dao().selectNotRecentlyDownloaded(repositoryId, daysAgo, deleteBatchSize());
      if (assetIds.length == 0) {
        break; // nothing left to purge
      }
      if ("H2".equals(thisSession().sqlDialect())) {
        // workaround lack of primitive array support in H2 (should be fixed in H2 1.4.201?)
        purged += dao().purgeSelectedAssets(stream(assetIds).boxed().toArray(Integer[]::new));
      }
      else {
        purged += dao().purgeSelectedAssets(assetIds);
      }

      preCommitEvent(() -> new AssetPrePurgeEvent(repositoryId, assetIds));
      postCommitEvent(() -> new AssetPurgedEvent(repositoryId, assetIds));

      commitChangesSoFar();
    }
    return purged;
  }

  /**
   * Generally it is recommended that this method not be called and let stores manage this value automatically.
   *
   * Sets the created time of the asset associated with the ID to the specified time.
   *
   * @since 3.29
   */
  @Transactional
  public void created(final FluentAssetImpl asset, final OffsetDateTime created) {
    dao().created(InternalIds.internalAssetId(asset), created);
  }

  /**
   * Generally it is recommended that this method not be called and let stores manage this value automatically.
   *
   * Sets the last download time of the asset associated with the ID to the specified time.
   *
   * @since 3.29
   */
  @Transactional
  public void lastDownloaded(final Asset asset, final OffsetDateTime lastDownloaded) {
    dao().lastDownloaded(InternalIds.internalAssetId(asset), lastDownloaded);
  }

  /**
   * Generally it is recommended that this method not be called and let stores manage this value automatically.
   *
   * Sets the last updated time of the asset associated with the ID to the specified time.
   *
   * @since 3.29
   */
  @Transactional
  public void lastUpdated(final Asset asset, final OffsetDateTime lastUpdated) {
    dao().lastUpdated(InternalIds.internalAssetId(asset), lastUpdated);
  }
}
