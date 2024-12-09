package org.sonatype.nexus.repository.npm.internal.tasks;

import org.sonatype.nexus.scheduling.Task;

/**
 * Task that reindexes npm proxy and hosted repositories by opening each tarball and extracting the contents of the
 * {@code package.json} as format attributes. This task is necessary to "upgrade" existing npm repositories to contain
 * the search-indexed format attributes necessary for npm v1 search.
 *
 * @since 3.7
 */
public interface ReindexNpmRepositoryTask
    extends Task
{
  public static final String NPM_V1_SEARCH_UNSUPPORTED = "npm_v1_search_unsupported";
}
