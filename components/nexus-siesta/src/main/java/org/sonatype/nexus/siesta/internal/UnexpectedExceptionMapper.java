package org.sonatype.nexus.siesta.internal;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.sonatype.nexus.rest.ExceptionMapperSupport;

/**
 * Unexpected generic {@link Throwable} exception mapper.
 *
 * This will handle all unexpected exceptions and override the default JAX-RS implementations behavior.
 *
 * @since 3.0
 */
@Named
@Singleton
@Provider
public class UnexpectedExceptionMapper
  extends ExceptionMapperSupport<Throwable>
{
  @Override
  protected Response convert(final Throwable exception, final String id) {
    return unexpectedResponse(exception, id);
  }
}
