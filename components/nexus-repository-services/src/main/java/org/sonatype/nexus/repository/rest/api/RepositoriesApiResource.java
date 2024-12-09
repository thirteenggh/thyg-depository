package org.sonatype.nexus.repository.rest.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.rest.WebApplicationMessageException;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

/**
 * @since 3.20
 */
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class RepositoriesApiResource
    extends ComponentSupport
    implements Resource, RepositoriesApiResourceDoc
{
  private final AuthorizingRepositoryManager authorizingRepositoryManager;

  @Inject
  public RepositoriesApiResource(final AuthorizingRepositoryManager authorizingRepositoryManager)
  {
    this.authorizingRepositoryManager = checkNotNull(authorizingRepositoryManager);
  }

  @Override
  @DELETE
  @Path("/{repositoryName}")
  @RequiresAuthentication
  public Response deleteRepository(@PathParam("repositoryName") final String repositoryName) throws Exception {
    boolean isDeleted = authorizingRepositoryManager.delete(repositoryName);
    return Response.status(isDeleted ? NO_CONTENT : NOT_FOUND).build();
  }

  @POST
  @Path("/{repositoryName}/rebuild-index")
  @RequiresAuthentication
  public void rebuildIndex(@PathParam("repositoryName") final String repositoryName) {
    try {
      authorizingRepositoryManager.rebuildSearchIndex(repositoryName);
    }
    catch (IncompatibleRepositoryException e) {
      log.debug("Not a hosted or proxy repository '{}'", repositoryName, e);
      throw new WebApplicationMessageException(BAD_REQUEST, "\"" + e.getMessage() + "\"", APPLICATION_JSON);
    }
    catch (RepositoryNotFoundException e) {
      log.debug("Repository not found '{}'", repositoryName, e);
      throw new WebApplicationMessageException(NOT_FOUND, "\"" + e.getMessage() + "\"", APPLICATION_JSON);
    }
  }

  @POST
  @Path("/{repositoryName}/invalidate-cache")
  @RequiresAuthentication
  public void invalidateCache(@PathParam("repositoryName") final String repositoryName) {
    try {
      authorizingRepositoryManager.invalidateCache(repositoryName);
    }
    catch (IncompatibleRepositoryException e) {
      log.debug("Not a proxy nor group repository '{}'", repositoryName, e);
      throw new WebApplicationMessageException(BAD_REQUEST, "\"" + e.getMessage() + "\"", APPLICATION_JSON);
    }
    catch (RepositoryNotFoundException e) {
      log.debug("Repository not found '{}'", repositoryName, e);
      throw new WebApplicationMessageException(NOT_FOUND, "\"" + e.getMessage() + "\"", APPLICATION_JSON);
    }
  }
}
