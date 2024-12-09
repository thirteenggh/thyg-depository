package org.sonatype.nexus.swagger.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.sonatype.nexus.rest.Resource;

import io.swagger.models.Swagger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Siesta-managed {@link io.swagger.jaxrs.listing.ApiListingResource}.
 * 
 * @since 3.3
 */
@Named
@Singleton
@Path("/swagger.{type:json|yaml}")
public class ApiListingResource
    extends io.swagger.jaxrs.listing.ApiListingResource
    implements Resource
{
  private final SwaggerModel swaggerModel;

  @Inject
  public ApiListingResource(final SwaggerModel swaggerModel) { // NOSONAR
    this.swaggerModel = checkNotNull(swaggerModel);
  }

  @Override
  protected Swagger process(final Application app,
                            final ServletContext servletContext,
                            final ServletConfig servletConfig,
                            final HttpHeaders httpHeaders,
                            final UriInfo uriInfo)
  {
    // update cached model to use base path calculated from incoming request
    return swaggerModel.getSwagger().basePath(uriInfo.getBaseUri().getPath());
  }
}
