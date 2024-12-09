package org.sonatype.nexus.siesta;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Echo Resource client.
 */
@Path("/echo")
@Produces(MediaType.APPLICATION_JSON)
public interface Echo
{
  @GET
  List<String> get(@QueryParam("foo") String foo);

  @GET
  List<String> get(@QueryParam("foo") String foo, @QueryParam("bar") int bar);

  @GET
  List<String> get(@QueryParam("bar") int bar);

  @GET
  @Path("/multiple")
  List<String> get(@QueryParam("foo") String[] foo);

  @GET
  @Path("/multiple")
  List<String> get(@QueryParam("foo") Object[] foo);

  @GET
  @Path("/multiple")
  List<String> get(@QueryParam("foo") Collection<?> foo);

  @GET
  @Path("/multiple")
  List<String> get(@QueryParam("foo") Iterator<?> foo);

  @GET
  List<String> get(@QueryParam("params") MultivaluedMap<String, String> queryParams);
}
