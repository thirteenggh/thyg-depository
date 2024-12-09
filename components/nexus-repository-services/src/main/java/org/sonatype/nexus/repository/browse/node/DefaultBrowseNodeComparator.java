package org.sonatype.nexus.repository.browse.node;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.app.VersionComparator;

/**
 * Sort using VersionComparator when dealing with two components, fall back to node name when dealing
 * with any other comparison of the same type (component/asset/folder), finally fall back to node type
 *
 * @since 3.13
 */
@Named(value = DefaultBrowseNodeComparator.NAME)
public class DefaultBrowseNodeComparator
    implements BrowseNodeComparator
{
  public static final String NAME = "default";

  private final VersionComparator versionComparator;

  private static final int TYPE_COMPONENT = 1;

  private static final int TYPE_FOLDER = 2;

  private static final int TYPE_ASSET = 3;

  @Inject
  public DefaultBrowseNodeComparator(final VersionComparator versionComparator) {
    this.versionComparator = versionComparator;
  }

  @Override
  public int compare(final BrowseNode o1, final BrowseNode o2) {
    int o1Type = getType(o1);
    int o2Type = getType(o2);

    if (o1Type == TYPE_COMPONENT && o2Type == TYPE_COMPONENT) {
      try {
        return versionComparator.compare(o1.getName(), o2.getName());
      }
      catch (IllegalArgumentException e) { //NOSONAR
        return 0;
      }
    }

    if (o1Type == o2Type) {
      return o1.getName().compareToIgnoreCase(o2.getName());
    }

    return Integer.compare(o1Type, o2Type);
  }

  protected int getType(final BrowseNode browseNode) {
    if (browseNode.getComponentId() != null) {
      return TYPE_COMPONENT;
    }

    if (browseNode.getAssetId() != null) {
      return TYPE_ASSET;
    }

    return TYPE_FOLDER;
  }
}
