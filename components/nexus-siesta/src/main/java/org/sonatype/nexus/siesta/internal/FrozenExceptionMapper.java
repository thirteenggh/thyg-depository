package org.sonatype.nexus.siesta.internal;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.sonatype.nexus.common.app.FrozenException;
import org.sonatype.nexus.rest.ExceptionMapperSupport;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

/**
 * {@link FrozenException} mapper.
 *
 * Handles exceptions generated when writes fail due to NXRM being in read-only mode.
 *
 * @since 3.21
 */
@Named
@Singleton
@Provider
public class FrozenExceptionMapper
    extends ExceptionMapperSupport<FrozenException>
{
  @Override
  protected Response convert(final FrozenException exception, final String id) {
    return Response.serverError()
        .status(SERVICE_UNAVAILABLE)
        .entity(String.format("Nexus Repository Manager is in read-only mode: (ID %s)", id))
        .type(TEXT_PLAIN)
        .build();
  }
}
