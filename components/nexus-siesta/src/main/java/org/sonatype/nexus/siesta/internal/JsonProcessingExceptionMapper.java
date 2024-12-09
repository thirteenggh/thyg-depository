package org.sonatype.nexus.siesta.internal;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.sonatype.nexus.rest.ExceptionMapperSupport;
import org.sonatype.nexus.rest.ValidationErrorXO;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Maps {@link JsonProcessingExceptionMapper} to {@link Status#BAD_REQUEST} in case there is a deserialization error.
 *
 * @since 3.19
 */
@Named
@Singleton
public class JsonProcessingExceptionMapper
    extends ExceptionMapperSupport<JsonProcessingException>
{
  @Override
  protected Response convert(final JsonProcessingException exception, final String id) {
    return Response.status(Status.BAD_REQUEST)
        .entity(new GenericEntity<>(new ValidationErrorXO(
            "Could not process the input: " + exception.getOriginalMessage()), ValidationErrorXO.class))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
