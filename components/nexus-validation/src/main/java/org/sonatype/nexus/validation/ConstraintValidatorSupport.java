package org.sonatype.nexus.validation;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.template.EscapeHelper;

/**
 * Support for {@link ConstraintValidator} implementations.
 *
 * @since 3.0
 */
public abstract class ConstraintValidatorSupport<A extends Annotation,T>
  extends ComponentSupport
  implements ConstraintValidator<A,T>
{
  private final EscapeHelper escapeHelper = new EscapeHelper();

  @Override
  public void initialize(final A constraintAnnotation) {
    // empty
  }

  protected EscapeHelper getEscapeHelper() {
    return escapeHelper;
  }
}
