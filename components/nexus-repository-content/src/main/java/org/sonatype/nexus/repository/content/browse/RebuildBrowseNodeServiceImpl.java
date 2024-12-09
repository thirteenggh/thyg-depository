package org.sonatype.nexus.repository.content.browse;

import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.browse.node.RebuildBrowseNodeFailedException;
import org.sonatype.nexus.repository.browse.node.RebuildBrowseNodeService;

import groovy.lang.Singleton;

/**
 * Rebuild browse node service for content repositories.
 *
 * @since 3.26
 */
@Named
@Singleton
public class RebuildBrowseNodeServiceImpl
    implements RebuildBrowseNodeService
{
  @Override
  public void rebuild(final Repository repository) throws RebuildBrowseNodeFailedException {
    repository.optionalFacet(BrowseFacet.class).ifPresent(BrowseFacet::rebuildBrowseNodes);
  }
}
