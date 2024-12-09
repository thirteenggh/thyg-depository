package org.sonatype.nexus.internal.security;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Security {@link LogConfigurationCustomizer}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration configuration) {
    configuration.setLoggerLevel("org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter", LoggerLevel.INFO);
    configuration.setLoggerLevel("org.apache.shiro.web.filter.mgt.DefaultFilterChainManager", LoggerLevel.INFO);
    configuration.setLoggerLevel("org.sonatype.nexus.security", LoggerLevel.DEFAULT);
    configuration.setLoggerLevel("org.sonatype.nexus.internal.security", LoggerLevel.DEFAULT);
  }
}
