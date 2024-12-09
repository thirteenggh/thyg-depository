package org.sonatype.nexus.internal.email;

import javax.annotation.Nullable;

import org.sonatype.nexus.email.EmailConfiguration;

/**
 * {@link EmailConfiguration} store.
 *
 * @since 3.0
 */
public interface EmailConfigurationStore
{
  @Nullable
  EmailConfiguration load();

  void save(EmailConfiguration configuration);

  /**
   * Create a new and empty {@link EmailConfiguration}
   * @since 3.20
   */
  EmailConfiguration newConfiguration();
}
