package org.sonatype.nexus.selector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link UniqueSelectorName} validator.
 *
 * @since 3.0
 */
@Named
public class UniqueSelectorNameValidator
    extends ConstraintValidatorSupport<UniqueSelectorName, String>
{
  private final SelectorManager selectorManager;

  @Inject
  public UniqueSelectorNameValidator(final SelectorManager selectorManager) {
    this.selectorManager = checkNotNull(selectorManager);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating unique selector name: {}", value);
    for (SelectorConfiguration configuration : selectorManager.browse()) {
      if (value.equals(configuration.getName())) {
        return false;
      }
    }
    return true;
  }
}
