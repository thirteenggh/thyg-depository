package org.sonatype.nexus.security.role;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.authz.AuthorizationManager;
import org.sonatype.nexus.security.authz.NoSuchAuthorizationManagerException;
import org.sonatype.nexus.security.internal.AuthorizationManagerImpl;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link UniqueRoleId} validator.
 *
 * @since 3.0
 */
@Named
public class UniqueRoleIdValidator
    extends ConstraintValidatorSupport<UniqueRoleId, String>
{
  private final AuthorizationManager authorizationManager;

  @Inject
  public UniqueRoleIdValidator(final SecuritySystem securitySystem) throws NoSuchAuthorizationManagerException {
    this.authorizationManager = checkNotNull(securitySystem).getAuthorizationManager(AuthorizationManagerImpl.SOURCE);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating unique role-id: {}", value);
    try {
      authorizationManager.getRole(value);
      return false;
    }
    catch (NoSuchRoleException e) {
      return true;
    }
  }
}
