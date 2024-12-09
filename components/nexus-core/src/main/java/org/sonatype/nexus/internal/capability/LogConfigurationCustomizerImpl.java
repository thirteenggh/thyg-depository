package org.sonatype.nexus.internal.capability;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Capabilities {@link LogConfigurationCustomizer}.
 *
 * @since 2.7
 */
@Singleton
@Named
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration configuration) {
    // Useful loggers (level will be calculated as effective level)
    configuration.setLoggerLevel("org.sonatype.nexus.capability", LoggerLevel.DEFAULT);
  }
}
