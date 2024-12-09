package org.sonatype.nexus.siesta.internal;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.sonatype.nexus.rest.ExceptionMapperSupport;
import org.sonatype.nexus.rest.ValidationErrorXO;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Maps {@link JsonMappingExceptionMapper} to {@link Status#BAD_REQUEST} in case there is a deserialization error.
 *
 * @since 3.19
 */
@Named
@Singleton
public class JsonMappingExceptionMapper
    extends ExceptionMapperSupport<JsonMappingException>
{
  @Override
  protected Response convert(final JsonMappingException exception, final String id) {
    final List<ValidationErrorXO> errors = exception.getPath().stream()
        .map(reference -> new ValidationErrorXO(reference.getFieldName(), exception.getOriginalMessage()))
        .collect(Collectors.toList());

    return Response.status(Status.BAD_REQUEST)
        .entity(new GenericEntity<List<ValidationErrorXO>>(errors)
        {
          @Override
          public String toString() {
            return getEntity().toString();
          }
        })
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
