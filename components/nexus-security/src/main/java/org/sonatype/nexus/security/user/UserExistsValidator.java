package org.sonatype.nexus.security.user;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link UserExists} validator.
 *
 * @since 3.0
 */
@Named
public class UserExistsValidator
    extends ConstraintValidatorSupport<UserExists, String>
{
  private final SecuritySystem securitySystem;

  @Inject
  public UserExistsValidator(final SecuritySystem securitySystem) {
    this.securitySystem = checkNotNull(securitySystem);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating user exists: {}", value);
    try {
      User user = securitySystem.getUser(value);
      return true;
    }
    catch (UserNotFoundException e) {
      return false;
    }
  }
}
