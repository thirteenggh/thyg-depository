package org.sonatype.nexus.repository.purge;

import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.scheduling.Cancelable;

/**
 * Task to purge unused components/assets of given repository.
 *
 * @since 3.0
 */
@Named
public class PurgeUnusedTask
    extends RepositoryTaskSupport
    implements Cancelable
{
  public static final String LAST_USED_FIELD_ID = "lastUsed";

  @Override
  protected void execute(final Repository repository) {
    repository.facet(PurgeUnusedFacet.class).purgeUnused(getConfiguration().getInteger(LAST_USED_FIELD_ID, -1));
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return repository.optionalFacet(PurgeUnusedFacet.class).isPresent();
  }

  @Override
  public String getMessage() {
    return "Purge unused components and assets from " + getRepositoryField();
  }
}
