package org.sonatype.nexus.siesta.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.orientechnologies.orient.server.distributed.ODistributedException;
import org.sonatype.nexus.rest.ExceptionMapperSupport;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

/**
 * {@link OWriteOperationNotPermittedException} exception mapper.
 *
 * This will handle exceptions generated when writes to orientdb fail due to being in replica mode.
 *
 * @since 3.6.0
 */
@Named
@Singleton
@Provider
public class OrientDistributedReadOnlyExceptionMapper
  extends ExceptionMapperSupport<ODistributedException>
{
  @Override
  protected Response convert(final ODistributedException exception, final String id) {
    // OWriteOperationNotPermittedException is not exported by orientdb
    if ("OWriteOperationNotPermittedException".equals(exception.getClass().getSimpleName())) {
      return Response.serverError()
          .status(SERVICE_UNAVAILABLE)
          .entity(String.format("Trust Repository Manager is in read-only mode: (ID %s)", id))
          .type(TEXT_PLAIN)
          .build();
    }
    else {
      return unexpectedResponse(exception, id);
    }
  }
}
