package org.sonatype.nexus.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import com.orientechnologies.orient.core.storage.impl.local.paginated.OLocalPaginatedStorage;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;

import static org.sonatype.nexus.common.log.LoggerLevel.DEFAULT;
import static org.sonatype.nexus.common.log.LoggerLevel.INFO;
import static org.sonatype.nexus.common.log.LoggerLevel.OFF;

/**
 * OrientDB {@link LogConfigurationCustomizer}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration config) {
    config.setLoggerLevel("org.sonatype.nexus.orient", DEFAULT);
    config.setLoggerLevel("org.sonatype.nexus.internal.orient", DEFAULT);
    config.setLoggerLevel("com.orientechnologies", DEFAULT);

    // leave explain logging off by default regardless of root level because it incurs a penalty
    // and disables the connection pool; so it's better to explicitly enable it when you need it
    config.setLoggerLevel("org.sonatype.nexus.orient.explain", OFF);

    // OLocalPaginatedStorage produces too much output if the ROOT logger is switched to DEBUG
    config.setLoggerLevel(OLocalPaginatedStorage.class.getName(), INFO);
  }
}
