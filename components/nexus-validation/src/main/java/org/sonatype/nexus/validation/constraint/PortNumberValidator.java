package org.sonatype.nexus.validation.constraint;

import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.validation.ConstraintValidatorSupport;

/**
 * {@link PortNumber} validator.
 *
 * @since 3.0
 */
public class PortNumberValidator
  extends ConstraintValidatorSupport<PortNumber,Integer>
{
  private int min;

  private int max;

  @Override
  public void initialize(final PortNumber annotation) {
    min = annotation.min();
    max = annotation.max();
  }

  @Override
  public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    return value >= min && value <= max;
  }
}
