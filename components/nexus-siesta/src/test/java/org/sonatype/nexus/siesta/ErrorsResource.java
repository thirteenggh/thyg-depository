package org.sonatype.nexus.siesta;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import org.sonatype.nexus.rest.Resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Named
@Singleton
@Path("/errors")
public class ErrorsResource
    implements Resource
{
  @GET
  @Path("/NotFoundException")
  @Produces({APPLICATION_XML, APPLICATION_JSON})
  public Object throwObjectNotFoundException() {
    throw new NotFoundException("NotFoundException");
  }

  @GET
  @Path("/BadRequestException")
  @Produces({APPLICATION_XML, APPLICATION_JSON})
  public Object throwBadRequestException() {
    throw new BadRequestException("BadRequestException");
  }

  @GET
  @Path("/406")
  @Produces({APPLICATION_XML, APPLICATION_JSON})
  public Object throw406() {
    throw new WebApplicationException(406);
  }
}
