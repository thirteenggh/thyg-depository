package org.sonatype.nexus.repository.rest.internal.resources;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.sonatype.nexus.repository.rest.api.RepositoryManagerRESTAdapter;
import org.sonatype.nexus.repository.rest.api.RepositoryXO;
import org.sonatype.nexus.repository.rest.internal.resources.doc.RepositoriesResourceDoc;
import org.sonatype.nexus.rest.Resource;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * @since 3.9
 */
@Named
@Singleton
@Path(RepositoriesResource.RESOURCE_URI)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class RepositoriesResource
    implements Resource, RepositoriesResourceDoc
{
  public static final String RESOURCE_URI = V1_API_PREFIX + "/repositories";

  private final RepositoryManagerRESTAdapter repositoryManagerRESTAdapter;

  @Inject
  public RepositoriesResource(final RepositoryManagerRESTAdapter repositoryManagerRESTAdapter) {
    this.repositoryManagerRESTAdapter = checkNotNull(repositoryManagerRESTAdapter);
  }

  @GET
  public List<RepositoryXO> getRepositories() {
    return repositoryManagerRESTAdapter.getRepositories()
        .stream()
        .map(RepositoryXO::fromRepository)
        .collect(toList());
  }
}
