package org.sonatype.nexus.security.privilege;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link UniquePrivilegeName} validator.
 *
 * @since 3.0
 */
@Named
public class UniquePrivilegeNameValidator
    extends ConstraintValidatorSupport<UniquePrivilegeName, String>
{
  private final SecuritySystem securitySystem;

  @Inject
  public UniquePrivilegeNameValidator(final SecuritySystem securitySystem) {
    this.securitySystem = checkNotNull(securitySystem);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating unique privilege name: {}", value);
    for (Privilege privilege : securitySystem.listPrivileges()) {
      if (value.equals(privilege.getName())) {
        return false;
      }
    }
    return true;
  }
}
