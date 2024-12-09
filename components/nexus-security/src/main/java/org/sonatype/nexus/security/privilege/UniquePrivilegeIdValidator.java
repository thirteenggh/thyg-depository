package org.sonatype.nexus.security.privilege;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link UniquePrivilegeId} validator.
 *
 * @since 3.0
 */
@Named
public class UniquePrivilegeIdValidator
    extends ConstraintValidatorSupport<UniquePrivilegeId, String>
{
  private final SecuritySystem securitySystem;

  @Inject
  public UniquePrivilegeIdValidator(final SecuritySystem securitySystem) {
    this.securitySystem = checkNotNull(securitySystem);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating unique privilege-id: {}", value);
    for (Privilege privilege : securitySystem.listPrivileges()) {
      if (value.equals(privilege.getId())) {
        return false;
      }
    }
    return true;
  }
}
