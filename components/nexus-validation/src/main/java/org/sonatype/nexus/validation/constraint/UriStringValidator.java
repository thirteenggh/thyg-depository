package org.sonatype.nexus.validation.constraint;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Validates a URI.
 *
 * @since 3.21
 */
public class UriStringValidator
    extends ConstraintValidatorSupport<UriString, String>
{
  @Override
  public boolean isValid(final String uri, final ConstraintValidatorContext constraintValidatorContext) {
    if (isBlank(uri)) {
      return true;
    }

    try {
      new URI(uri);
      return true;
    }
    catch (URISyntaxException ignore) { // NOSONAR
      return false;
    }
  }
}
