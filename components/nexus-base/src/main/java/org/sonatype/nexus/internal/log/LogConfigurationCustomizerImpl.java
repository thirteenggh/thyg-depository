package org.sonatype.nexus.internal.log;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

@Named
@Singleton
public class LogConfigurationCustomizerImpl
    implements LogConfigurationCustomizer
{
  @Override
  public void customize(final Configuration configuration) {
    configuration.setLoggerLevel("org.apache.commons", LoggerLevel.WARN);

    configuration.setLoggerLevel("org.eclipse.jetty", LoggerLevel.INFO);
    configuration.setLoggerLevel("eu.medsea.mimeutil.MimeUtil2", LoggerLevel.INFO);

    // NEXUS-5456: limit noisy guice timing logger
    configuration.setLoggerLevel("com.google.inject.internal.util.Stopwatch", LoggerLevel.INFO);

    configuration.setLoggerLevel("javax.management", LoggerLevel.INFO);
    configuration.setLoggerLevel("sun.rmi", LoggerLevel.INFO);

    // Useful loggers (level will be calculated as effective level)
    configuration.setLoggerLevel("org.sonatype.nexus", LoggerLevel.DEFAULT);

    configuration.setLoggerLevel("org.sonatype.nexus.jmx", LoggerLevel.DEFAULT);
    configuration.setLoggerLevel("org.sonatype.nexus.internal.log", LoggerLevel.DEFAULT);
    configuration.setLoggerLevel("org.sonatype.nexus.plugins", LoggerLevel.DEFAULT);
  }
}
