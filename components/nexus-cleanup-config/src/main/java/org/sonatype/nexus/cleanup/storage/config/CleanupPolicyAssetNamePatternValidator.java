package org.sonatype.nexus.cleanup.storage.config;

import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.cleanup.storage.config.RegexCriteriaValidator.InvalidExpressionException;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static java.util.Objects.nonNull;
import static org.sonatype.nexus.cleanup.storage.config.RegexCriteriaValidator.validate;

/**
 *
 * @since 3.19
 */
@Named
public class CleanupPolicyAssetNamePatternValidator
    extends ConstraintValidatorSupport<CleanupPolicyAssetNamePattern, String>
{
  public CleanupPolicyAssetNamePatternValidator() {
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    // we don't require it
    if (nonNull(value)) {

      try {
        // but if it is provided we require it to be valid
        validate(value);
      }
      catch (InvalidExpressionException e) { // NOSONAR
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(getEscapeHelper().stripJavaEl(e.getMessage())).addConstraintViolation();
        return false;
      }
    }

    return true;
  }
}
