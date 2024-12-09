package org.sonatype.nexus.siesta.internal;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.sonatype.nexus.rest.ExceptionMapperSupport;

/**
 * Standard {@link WebApplicationException} exception mapper.
 *
 * This is needed to restore default response behavior when {@link UnexpectedExceptionMapper} is installed.
 *
 * @since 3.0
 */
@Named
@Singleton
@Provider
public class WebappExceptionMapper
  extends ExceptionMapperSupport<WebApplicationException>
{
  @Override
  protected Response convert(final WebApplicationException exception, final String id) {
    return exception.getResponse();
  }
}
