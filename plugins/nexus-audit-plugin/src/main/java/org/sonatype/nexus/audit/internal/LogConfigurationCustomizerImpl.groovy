package org.sonatype.nexus.audit.internal

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.log.LogConfigurationCustomizer
import org.sonatype.nexus.common.log.LoggerLevel

/**
 * Audit {@link LogConfigurationCustomizer}.
 *
 * @since 3.1
 */
@Named
@Singleton
class LogConfigurationCustomizerImpl
    extends ComponentSupport
    implements LogConfigurationCustomizer
{
  @Override
  void customize(final LogConfigurationCustomizer.Configuration configuration) {
    configuration.setLoggerLevel('org.sonatype.nexus.audit', LoggerLevel.DEFAULT)
  }
}
