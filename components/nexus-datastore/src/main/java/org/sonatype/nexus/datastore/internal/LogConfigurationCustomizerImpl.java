package org.sonatype.nexus.datastore.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;

import static org.sonatype.nexus.common.log.LoggerLevel.DEFAULT;

/**
 * DataStore {@link LogConfigurationCustomizer}.
 *
 * @since 3.19
 */
@Named
@Singleton
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration config) {
    config.setLoggerLevel("org.sonatype.nexus.datastore", DEFAULT);
  }
}
