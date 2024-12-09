package org.sonatype.nexus.repository.pypi.internal;

import javax.inject.Named;

import org.sonatype.nexus.repository.browse.node.BrowseNode;
import org.sonatype.nexus.repository.browse.node.BrowseNodeIdentity;

/**
 * PyPi custom {@link BrowseNodeIdentity}
 *
 * @since 3.22
 */
@Named(PyPiFormat.NAME)
public class PyPiBrowseNodeIdentity
    implements BrowseNodeIdentity
{
  /**
   * Changes default behavior to be distinct and ignore case
   */
  @Override
  public String identity(final BrowseNode browseNode) {
    return browseNode.getName().toLowerCase();
  }
}
