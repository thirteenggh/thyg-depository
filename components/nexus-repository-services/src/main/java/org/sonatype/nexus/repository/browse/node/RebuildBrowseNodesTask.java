package org.sonatype.nexus.repository.browse.node;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.scheduling.Cancelable;

/**
 * Browse nodes rebuild task.
 *
 * @since 3.6
 */
@Named
public class RebuildBrowseNodesTask
    extends RepositoryTaskSupport
    implements Cancelable
{
  private final RebuildBrowseNodeService rebuildBrowseNodeService;

  private static final String PYPI_FORMAT_NAME = "pypi";

  @Inject
  public RebuildBrowseNodesTask(final RebuildBrowseNodeService rebuildBrowseNodeService)
  {
    this.rebuildBrowseNodeService = rebuildBrowseNodeService;
  }

  @Override
  public String getMessage() {
    return "Rebuilding browse tree for " + getRepositoryField();
  }

  @Override
  protected void execute(final Repository repo) {
    try {
      delayIfPyPi(repo);
      rebuildBrowseNodeService.rebuild(repo);
    }
    catch (RebuildBrowseNodeFailedException e) {
      log.error("Error rebuilding browse nodes for repository: {}", repo, e);
    }
  }

  @SuppressWarnings("java:S2142") // we cannot rethrow exception and we don't want to interrupt the current thread
  private void delayIfPyPi(final Repository repo) {
    if (PYPI_FORMAT_NAME.equals(repo.getFormat().getValue()) && ProxyType.NAME.equals(repo.getType().getValue())) {
      try {
        log.info("Delaying rebuild browse node task for repository {} for 30 seconds", repo);
        TimeUnit.SECONDS.sleep(30);
      }
      catch (InterruptedException e) {
        log.warn("Problem delaying rebuild for PyPI repository: {}", e.getMessage());
      }
    }
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return repository != null;
  }
}
