package com.sonatype.nexus.ssl.plugin.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Configures SSL loggers.
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
    configuration.setLoggerLevel("com.sonatype.nexus.ssl", LoggerLevel.DEFAULT);
  }
}
