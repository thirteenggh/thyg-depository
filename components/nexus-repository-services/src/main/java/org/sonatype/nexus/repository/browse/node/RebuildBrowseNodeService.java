package org.sonatype.nexus.repository.browse.node;

import org.sonatype.nexus.repository.Repository;

/**
 * Service for rebuilding browse node data.
 *
 * @since 3.22
 */
public interface RebuildBrowseNodeService
{
  /**
   * @param repository {@link Repository} to rebuild browse node data for.
   */
  void rebuild(Repository repository) throws RebuildBrowseNodeFailedException;
}
