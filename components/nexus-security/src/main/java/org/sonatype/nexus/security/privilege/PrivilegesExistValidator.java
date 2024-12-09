package org.sonatype.nexus.security.privilege;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;
import org.sonatype.nexus.validation.constraint.NamePatternConstants;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link PrivilegesExist} validator.
 *
 * @since 3.0
 */
@Named
public class PrivilegesExistValidator
    extends ConstraintValidatorSupport<PrivilegesExist, Collection<?>> // Collection<String> expected
{
  private static final String MESSAGE =
      "Only letters, digits, underscores(_), hyphens(-), dots(.), and asterisks(*) are allowed and may not start with underscore or dot.";

  private final SecuritySystem securitySystem;

  @Inject
  public PrivilegesExistValidator(final SecuritySystem securitySystem) {
    this.securitySystem = checkNotNull(securitySystem);
  }

  @Override
  public boolean isValid(final Collection<?> value, final ConstraintValidatorContext context) {
    log.trace("Validating privileges exist: {}", value);
    Set<String> ids = new HashSet<>();
    for (Privilege privilege : securitySystem.listPrivileges()) {
      ids.add(privilege.getId());
    }

    List<Object> missing = new LinkedList<>();
    for (Object item : value) {
      String privilegeId = String.valueOf(item);
      if (!privilegeId.matches(NamePatternConstants.REGEX_WITH_WILDCARDS)) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
            "Invalid privilege id: " + getEscapeHelper().stripJavaEl(privilegeId) + ". " + MESSAGE)
            .addConstraintViolation();
        return false;
      }
      if (!ids.contains(item)) {
        missing.add(getEscapeHelper().stripJavaEl(item.toString()));
      }
    }
    if (missing.isEmpty()) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("Missing privileges: " + missing)
        .addConstraintViolation();
    return false;
  }
}
