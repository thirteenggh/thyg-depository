package org.sonatype.nexus.security.config;

/**
 * Source of {@link SecurityConfiguration}.
 */
public interface SecurityConfigurationSource
{
  /**
   * Gets the current configuration.
   *
   * @return the configuration, null if not loaded
   */
  SecurityConfiguration getConfiguration();

  /**
   * Forces reloading the user configuration.
   *
   * @return the configuration
   */
  SecurityConfiguration loadConfiguration();
}
