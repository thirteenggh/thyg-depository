package org.sonatype.nexus.repository.config;

import org.sonatype.nexus.repository.config.internal.ConfigurationObjectMapperProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Customizes {@link ObjectMapper} for repository configuration.
 *
 * @since 3.0
 * @see ConfigurationObjectMapperProvider
 */
public interface ConfigurationObjectMapperCustomizer
{
  /**
   * Applies any customization to the passed in {@link ObjectMapper}, is never {@code null}.
   */
  void customize(ObjectMapper objectMapper);
}
