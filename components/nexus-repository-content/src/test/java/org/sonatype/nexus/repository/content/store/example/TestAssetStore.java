package org.sonatype.nexus.repository.content.store.example;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.store.AssetStore;
import org.sonatype.nexus.transaction.Transactional;

import com.google.inject.assistedinject.Assisted;

/**
 * Enhanced test asset store.
 */
public class TestAssetStore
    extends AssetStore<TestAssetDAO>
{
  @Inject
  public TestAssetStore(final DataSessionSupplier sessionSupplier,
                        @Assisted final String storeName)
  {
    super(sessionSupplier, storeName, TestAssetDAO.class);
  }

  /**
   * Browse all flagged assets in the given repository in a paged fashion.
   *
   * @param repositoryId the repository to browse
   * @param limit maximum number of assets to return
   * @param continuationToken optional token to continue from a previous request
   * @return collection of assets and the next continuation token
   *
   * @see Continuation#nextContinuationToken()
   */
  @Transactional
  public Continuation<Asset> browseFlaggedAssets(final int repositoryId,
                                                 final int limit,
                                                 @Nullable final String continuationToken)
  {
    return dao().browseFlaggedAssets(repositoryId, limit, continuationToken);
  }

  /**
   * Updates the test flag in the given asset.
   *
   * @param asset the asset to update
   */
  @Transactional
  public void updateAssetFlag(final TestAssetData asset) {
    dao().updateAssetFlag(asset);
  }
}
