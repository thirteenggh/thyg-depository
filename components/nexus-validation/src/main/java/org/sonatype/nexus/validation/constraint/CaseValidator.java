package org.sonatype.nexus.validation.constraint;

import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

/**
 * {@link Case} validator.
 *
 * @since 3.0
 */
public class CaseValidator
    extends ConstraintValidatorSupport<Case, String>
{
  private CaseType type;

  @Override
  public void initialize(final Case annotation) {
    type = annotation.value();
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    if (type == CaseType.UPPER) {
      return value.equals(Strings2.upper(value));
    }
    else {
      return value.equals(Strings2.lower(value));
    }
  }
}
