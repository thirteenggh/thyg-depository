package org.sonatype.nexus.security.role;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
 * {@link RolesExist} validator.
 *
 * @since 3.0
 */
@Named
public class RolesExistValidator
    extends ConstraintValidatorSupport<RolesExist, Collection<?>> // Collection<String> expected
{
  private final AuthorizationManager authorizationManager;

  @Inject
  public RolesExistValidator(final SecuritySystem securitySystem) throws NoSuchAuthorizationManagerException {
    this.authorizationManager = checkNotNull(securitySystem).getAuthorizationManager(AuthorizationManagerImpl.SOURCE);
  }

  @Override
  public boolean isValid(final Collection<?> value, final ConstraintValidatorContext context) {
    log.trace("Validating roles exist: {}", value);
    List<Object> missing = new LinkedList<>();
    for (Object item : value) {
      try {
        authorizationManager.getRole(String.valueOf(item));
      }
      catch (NoSuchRoleException e) {
        missing.add(getEscapeHelper().stripJavaEl(item.toString()));
      }
    }
    if (missing.isEmpty()) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("Missing roles: " + missing)
        .addConstraintViolation();
    return false;
  }
}
