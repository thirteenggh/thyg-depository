package org.sonatype.nexus.siesta.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.orientechnologies.common.concur.lock.OModificationOperationProhibitedException;
import org.sonatype.nexus.rest.ExceptionMapperSupport;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

/**
 * {@link OModificationOperationProhibitedException} exception mapper.
 *
 * This will handle exceptions generated when writes to orientdb fail due to being in read-only mode.
 *
 * @since 3.6.0
 */
@Named
@Singleton
@Provider
public class OrientReadOnlyExceptionMapper
  extends ExceptionMapperSupport<OModificationOperationProhibitedException>
{
  @Override
  protected Response convert(final OModificationOperationProhibitedException exception, final String id) {
    return Response.serverError()
        .status(SERVICE_UNAVAILABLE)
        .entity(String.format("Trust Repository Manager is in read-only mode: (ID %s)", id))
        .type(TEXT_PLAIN)
        .build();
  }
}
