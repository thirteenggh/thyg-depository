package org.sonatype.nexus.repository.rest.api;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiHostedRepository;
import org.sonatype.nexus.validation.Validate;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

/**
 * @since 3.28
 */
public class AbstractHostedRepositoriesApiResource<T extends HostedRepositoryApiRequest>
    extends AbstractRepositoriesApiResource<T>
{
  @GET
  @Path("/{repositoryName}")
  @RequiresAuthentication
  @Validate
  @ApiOperation(value = "Get repository", response = SimpleApiHostedRepository.class)
  @Override
  public AbstractApiRepository getRepository(@ApiParam(hidden = true) @BeanParam final FormatAndType formatAndType,
                                             @PathParam("repositoryName") final String repositoryName)
  {
    return super.getRepository(formatAndType, repositoryName);
  }
}
