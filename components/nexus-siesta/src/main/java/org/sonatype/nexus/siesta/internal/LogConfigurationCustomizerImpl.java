package org.sonatype.nexus.siesta.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;

import static org.sonatype.nexus.common.log.LoggerLevel.DEFAULT;

/**
 * Siesta {@link LogConfigurationCustomizer}.
 *
 * @since 2.7
 */
@Named
@Singleton
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration configuration) {
    configuration.setLoggerLevel("org.sonatype.nexus.rest", DEFAULT);
    configuration.setLoggerLevel("org.sonatype.nexus.plugins.siesta", DEFAULT);
    configuration.setLoggerLevel("org.jboss.resteasy", DEFAULT);
  }
}
