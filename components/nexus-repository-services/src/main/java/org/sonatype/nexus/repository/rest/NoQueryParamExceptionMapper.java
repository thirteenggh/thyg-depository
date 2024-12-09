package org.sonatype.nexus.repository.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.rest.ExceptionMapperSupport;

import static org.sonatype.nexus.rest.SimpleApiResponse.badRequest;

/**
 * @since 3.14
 */
@Named
@Singleton
public class NoQueryParamExceptionMapper
    extends ExceptionMapperSupport<NoQueryParamException>
{
  @Override
  protected Response convert(final NoQueryParamException exception, final String id) {
    return badRequest(exception.getMessage());
  }
}
