package org.sonatype.nexus.extdirect.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Ext.Direct {@link LogConfigurationCustomizer}.
 *
 * @since 3.0
 */
@Singleton
@Named
public class ExtDirectLogConfigurationCustomizer
    implements LogConfigurationCustomizer
{

  @Override
  public void customize(final Configuration configuration) {
    // Useful loggers (level will be calculated as effective level)
    configuration.setLoggerLevel("org.sonatype.nexus.extdirect", LoggerLevel.DEFAULT);
    configuration.setLoggerLevel("com.softwarementors.extjs.djn", LoggerLevel.DEFAULT);
  }

}
