package org.sonatype.nexus.repository.rest.internal.resources;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.rest.api.RepositoryManagerRESTAdapter;
import org.sonatype.nexus.repository.security.RepositoryPermissionChecker;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;
import static org.sonatype.nexus.repository.http.HttpStatus.FORBIDDEN;
import static org.sonatype.nexus.repository.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * An implementation of the {@link RepositoryManagerRESTAdapter}
 *
 * @since 3.4
 */
@Named
public class RepositoryManagerRESTAdapterImpl
    implements RepositoryManagerRESTAdapter
{
  private final RepositoryManager repositoryManager;

  private final RepositoryPermissionChecker repositoryPermissionChecker;

  @Inject
  public RepositoryManagerRESTAdapterImpl(final RepositoryManager repositoryManager,
                                          final RepositoryPermissionChecker repositoryPermissionChecker)
  {
    this.repositoryManager = checkNotNull(repositoryManager);
    this.repositoryPermissionChecker = checkNotNull(repositoryPermissionChecker);
  }

  @Override
  public Repository getRepository(final String repositoryId) {
    if (repositoryId == null) {
      throw new WebApplicationException("repositoryId is required.", UNPROCESSABLE_ENTITY);
    }
    Repository repository = ofNullable(repositoryManager.get(repositoryId))
        .orElseThrow(() -> new NotFoundException("Unable to locate repository with id " + repositoryId));

    if (repositoryPermissionChecker.userCanBrowseRepository(repository)) {
      //browse implies complete access to the repository.
      return repository;
    }
    else {
      //repository exists but user does not have the appropriate permission to browse, return a 403
      throw new WebApplicationException(FORBIDDEN);
    }
  }

  @Override
  public List<Repository> getRepositories() {
    return repositoryPermissionChecker.userCanBrowseRepositories(repositoryManager.browse());
  }

  @Override
  public List<String> findContainingGroups(final String repositoryName) {
    return repositoryManager.findContainingGroups(repositoryName);
  }
}
