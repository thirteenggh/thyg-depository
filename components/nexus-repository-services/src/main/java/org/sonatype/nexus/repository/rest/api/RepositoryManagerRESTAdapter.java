package org.sonatype.nexus.repository.rest.api;

import java.util.List;

import org.sonatype.nexus.repository.Repository;

/**
 * A component to share common functionality for interacting with {@link
 * org.sonatype.nexus.repository.manager.RepositoryManager}.
 * between API resources
 *
 * @since 3.4
 */
public interface RepositoryManagerRESTAdapter
{
  /**
   * Retrieve a repository. Will throw a {@link javax.ws.rs.WebApplicationException} with status code 422 if the
   * supplied  repository id is null, and throws a {@link javax.ws.rs.NotFoundException} if no repository with the
   * supplied id exists.
   */
  Repository getRepository(String repositoryId);

  /**
   * Retrieve all repositories that the user access to.
   */
  List<Repository> getRepositories();

  /**
   * Retrieves all group repository names that the specified repository is a member of.
   */
  List<String> findContainingGroups(String repositoryName);
}
