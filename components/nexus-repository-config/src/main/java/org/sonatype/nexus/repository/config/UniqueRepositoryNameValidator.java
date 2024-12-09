package org.sonatype.nexus.repository.config;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Ensure that Repository names are unique case-insensitively.
 *
 * @since 3.0
 */
@Named
public class UniqueRepositoryNameValidator
    extends ConstraintValidatorSupport<UniqueRepositoryName, String>
{
  private final RepositoryManager repositoryManager;

  @Inject
  public UniqueRepositoryNameValidator(final RepositoryManager repositoryManager) {
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return !repositoryManager.exists(value);
  }
}
