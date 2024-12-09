package org.sonatype.nexus.repository.manager;

import javax.validation.ConstraintViolation;

import org.sonatype.nexus.repository.config.Configuration;

/**
 * Configuration checks can be conducted for any new or updated repositories
 *
 * @since 3.19
 */
public interface ConfigurationValidator
{
  /**
   * Validates against the new configuration
   *
   * @param configuration
   * @return {@code null} of no error otherwise a {@link ConstraintViolation} describing the error.
   */
  ConstraintViolation<?> validate(final Configuration configuration);
}
