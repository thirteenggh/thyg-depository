package org.sonatype.nexus.email.internal;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.email.EmailConfiguration;

/**
 * Initial {@link EmailConfiguration} populator.
 * Fills in the object with defaults
 *
 * @since 3.0
 */
@Named("initial")
@Singleton
public class InitialEmailConfigurationPopulator
    implements Function<EmailConfiguration, EmailConfiguration>
{
  @Override
  public EmailConfiguration apply(final EmailConfiguration configuration) {
    configuration.setEnabled(false);
    configuration.setHost("localhost");
    configuration.setPort(25);
    configuration.setFromAddress("nexus@example.org");
    return configuration;
  }
}
