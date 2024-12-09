package org.sonatype.nexus.repository.search.index;

import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.scheduling.Cancelable;

/**
 * Internal task to rebuild index of given repository.
 *
 * @since 3.0
 */
@Named
public class RebuildIndexTask
    extends RepositoryTaskSupport
    implements Cancelable
{
  @Override
  protected void execute(final Repository repository) {
    repository.facet(SearchIndexFacet.class).rebuildIndex();
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return repository.optionalFacet(SearchIndexFacet.class).isPresent();
  }

  @Override
  public String getMessage() {
    return "Rebuilding search index of " + getRepositoryField();
  }
}
