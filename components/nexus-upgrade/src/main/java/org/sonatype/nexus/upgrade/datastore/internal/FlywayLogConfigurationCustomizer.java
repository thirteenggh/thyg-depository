package org.sonatype.nexus.upgrade.datastore.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Configures Flyway loggers.
 *
 * @since 3.29
 */
@Singleton
@Named
public class FlywayLogConfigurationCustomizer
    implements LogConfigurationCustomizer
{

  @Override
  public void customize(final Configuration configuration) {
    // limit noisy flyway loggers
    configuration.setLoggerLevel("org.flywaydb.core.internal", LoggerLevel.ERROR);
  }
}
