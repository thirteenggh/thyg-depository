package org.sonatype.nexus.siesta.internal;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

import org.sonatype.nexus.rest.ValidationErrorXO;
import org.sonatype.nexus.rest.ValidationErrorsException;
import org.sonatype.nexus.siesta.ValidationExceptionMapperSupport;

/**
 * Maps {@link ValidationErrorsException} to 400 with a list of {@link ValidationErrorXO} as body.
 *
 * @since 3.0
 */
@Named
@Singleton
@Provider
public class ValidationErrorsExceptionMapper
    extends ValidationExceptionMapperSupport<ValidationErrorsException>
{
  @Override
  protected List<ValidationErrorXO> getValidationErrors(final ValidationErrorsException exception) {
    return exception.getValidationErrors();
  }
}
