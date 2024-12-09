package org.sonatype.nexus.quartz.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Quartz {@link LogConfigurationCustomizer}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class LogConfigurationCustomizerImpl
    extends ComponentSupport
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final LogConfigurationCustomizer.Configuration configuration) {
    configuration.setLoggerLevel("org.sonatype.nexus.quartz", LoggerLevel.DEFAULT);

    // Quartz is chatty at INFO
    configuration.setLoggerLevel("org.quartz", LoggerLevel.WARN);
  }
}
