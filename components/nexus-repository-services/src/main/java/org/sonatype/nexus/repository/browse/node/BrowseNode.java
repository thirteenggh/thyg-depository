package org.sonatype.nexus.repository.browse.node;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityId;

/**
 * Represents a path segment in a tree hierarchy.
 *
 * @since 3.6
 */
public interface BrowseNode
{
  /**
   * @since 3.18
   */
  String getPath();

  /**
   * @since 3.7
   */
  String getName();

  /**
   * @since 3.6.1
   */
  boolean isLeaf();

  @Nullable
  EntityId getComponentId();

  @Nullable
  String getPackageUrl();

  @Nullable
  EntityId getAssetId();
}
