package org.sonatype.nexus.repository.storage;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;

import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.index.OIndexCursor;

/**
 * Store providing access to components.
 *
 * @since 3.6
 */
public interface ComponentStore
{
  /**
   * @return the component for the id
   */
  Component read(EntityId id);

  /**
   * Finds and returns all the components that match the specified parameters.
   *
   * @return All the components that match the specified parameters
   * @since 3.14
   */
  List<Component> getAllMatchingComponents(final Repository repository,
                                           final String group,
                                           final String name,
                                           final Map<String, String> formatAttributes);

  /**
   * @return the number of components contained by the buckets
   */
  long countComponents(@Nullable final Iterable<Bucket> buckets);

  /**
   * Get an index to iterate over the assets in that index
   */
  OIndex<?> getIndex(final String indexName);

  /**
   * @param cursor to get the asset ids from
   * @param limit  the maximum number of records to return
   * @return a page of assets in a bucket (up to limit number of assets or the end of the records available)
   */
  <T> List<Entry<T, EntityId>> getNextPage(final OIndexCursor cursor, final int limit);
}
