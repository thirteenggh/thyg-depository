package org.sonatype.nexus.coreui;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Core UI {@link LogConfigurationCustomizer}.
 *
 * @since 3.0
 */
@Singleton
@Named
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration configuration) {
    configuration.setLoggerLevel("org.sonatype.nexus.coreui", LoggerLevel.DEFAULT);
  }
}
