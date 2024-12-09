package org.sonatype.nexus.repository.storage;

import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityId;

import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.index.OIndexCursor;

/**
 * Store providing access to assets.
 *
 * @since 3.6
 */
public interface AssetStore
{
  /**
   * Get the asset matching the id or null
   */
  Asset getById(EntityId id);

  /**
   * Get the assets matching the ids or an empty iterable
   */
  Iterable<Asset> getByIds(Iterable<EntityId> id);

  /**
   * Gets the number of assets matching the given {@link Query} clause.
   */
  long countAssets(@Nullable Iterable<Bucket> buckets);

  /**
   * Get an index to iterate over the assets in that index
   */
  OIndex<?> getIndex(String indexName);

  /**
   * @param cursor to get the asset ids from
   * @param limit the maximum number of records to return
   * @return a page of assets in a bucket (up to limit number of assets or the end of the records available)
   */
  <T> List<Entry<T, EntityId>> getNextPage(OIndexCursor cursor, int limit);

  /**
   * Save changes made to an asset
   */
  Asset save(Asset asset);

  /**
   * Save changes to multiple assets
   */
  void save(Iterable<Asset> assets);
}
