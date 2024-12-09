package org.sonatype.nexus.repository.rest.api;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import static org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class RepositoriesApiResourceV1
  extends RepositoriesApiResource
{
  public static final String RESOURCE_URI = V1_API_PREFIX + "/repositories";

  @Inject
  public RepositoriesApiResourceV1(final AuthorizingRepositoryManager authorizingRepositoryManager)
  {
    super(authorizingRepositoryManager);
  }
}
