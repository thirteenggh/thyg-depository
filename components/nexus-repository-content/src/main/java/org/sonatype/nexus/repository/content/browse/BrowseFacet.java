package org.sonatype.nexus.repository.content.browse;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.browse.node.BrowseNode;

/**
 * Browse {@link Facet} that maintains the browse tree.
 *
 * @since 3.26
 */
@Facet.Exposed
public interface BrowseFacet
    extends Facet
{
  /**
   * Retrieves the browse nodes directly under the given hierarchical display path.
   *
   * @param displayPath the hierarchical path leading up to the browse nodes
   * @param limit when positive limits the number of browse nodes returned
   * @param filter optional filter to apply to the browse nodes
   * @param filterParams parameter map for the optional filter
   * @return browse nodes found directly under the display path
   */
  List<BrowseNode> getByDisplayPath(
      List<String> displayPath,
      int limit,
      @Nullable String filter,
      @Nullable Map<String, Object> filterParams);

  /**
   * Adds the necessary browse nodes leading up to these assets and their components.
   *
   * @param assetIds the assets to add
   */
  void addPathsToAssets(Collection<EntityId> assetIds);

  /**
   * Trims any dangling browse nodes from this repository.
   */
  void trimBrowseNodes();

  /**
   * Rebuilds the browse node tree for this repository.
   */
  void rebuildBrowseNodes();
}
