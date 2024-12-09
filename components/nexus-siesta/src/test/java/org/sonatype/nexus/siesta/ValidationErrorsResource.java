package org.sonatype.nexus.siesta;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.rest.ValidationErrorsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Named
@Singleton
@Path("/validationErrors")
public class ValidationErrorsResource
    implements Resource
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @PUT
  @Path("/manual/multiple")
  @Consumes({APPLICATION_XML, APPLICATION_JSON})
  @Produces({APPLICATION_XML, APPLICATION_JSON})
  public UserXO putWithMultipleManualValidations(final UserXO user) {
    log.info("PUT user: {}", user);

    final ValidationErrorsException validationErrors = new ValidationErrorsException();
    if (user.getName() == null) {
      validationErrors.withError("name", "Name cannot be null");
    }
    if (user.getDescription() == null) {
      validationErrors.withError("description", "Description cannot be null");
    }

    if (validationErrors.hasValidationErrors()) {
      throw validationErrors;
    }

    return user;
  }

  @PUT
  @Path("/manual/single")
  @Consumes({APPLICATION_XML, APPLICATION_JSON})
  @Produces({APPLICATION_XML, APPLICATION_JSON})
  public UserXO putWithSingleManualValidation(final UserXO user) {
    log.info("PUT user: {}", user);

    if (user.getName() == null) {
      throw new ValidationErrorsException("name", "Name cannot be null");
    }
    if (user.getDescription() == null) {
      throw new ValidationErrorsException("description", "Description cannot be null");
    }

    return user;
  }
}
