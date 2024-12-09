package org.sonatype.nexus.security.user;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link UniqueUserId} validator.
 *
 * @since 3.0
 */
@Named
public class UniqueUserIdValidator
    extends ConstraintValidatorSupport<UniqueUserId, String>
{
  private final UserManager userManager;

  @Inject
  public UniqueUserIdValidator(final SecuritySystem securitySystem) throws NoSuchUserManagerException {
    this.userManager = checkNotNull(securitySystem).getUserManager(UserManager.DEFAULT_SOURCE);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating unique user-id: {}", value);
    try {
      userManager.getUser(value);
      return false;
    }
    catch (UserNotFoundException e) {
      return true;
    }
  }
}
