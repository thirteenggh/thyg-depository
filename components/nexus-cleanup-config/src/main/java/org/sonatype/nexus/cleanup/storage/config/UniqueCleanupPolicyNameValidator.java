package org.sonatype.nexus.cleanup.storage.config;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.cleanup.storage.CleanupPolicyStorage;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Ensure that Cleanup Policy names are unique case-insensitively.
 *
 * @since 3.14
 */
@Named
public class UniqueCleanupPolicyNameValidator
    extends ConstraintValidatorSupport<UniqueCleanupPolicyName, String>
{
  private CleanupPolicyStorage cleanupPolicyStorage;

  @Inject
  public UniqueCleanupPolicyNameValidator(final CleanupPolicyStorage cleanupPolicyStorage) {
    this.cleanupPolicyStorage = checkNotNull(cleanupPolicyStorage);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return !cleanupPolicyStorage.exists(value);
  }
}
