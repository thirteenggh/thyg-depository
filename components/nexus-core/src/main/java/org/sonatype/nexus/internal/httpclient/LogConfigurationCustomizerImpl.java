package org.sonatype.nexus.internal.httpclient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

import static org.sonatype.nexus.internal.httpclient.HttpClientManagerImpl.HTTPCLIENT_OUTBOUND_LOGGER_NAME;

/**
 * HTTP-client {@link LogConfigurationCustomizer}.
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
    // NEXUS-6134: make it easy for user to debug outbound request headers
    config.setLoggerLevel("org.apache.http", LoggerLevel.INFO);
    config.setLoggerLevel("org.apache.http.wire", LoggerLevel.ERROR);

    config.setLoggerLevel("org.sonatype.nexus.httpclient", LoggerLevel.DEFAULT);
    config.setLoggerLevel("org.sonatype.nexus.internal.httpclient", LoggerLevel.DEFAULT);

    config.setLoggerLevel(HTTPCLIENT_OUTBOUND_LOGGER_NAME, LoggerLevel.DEFAULT);
  }
}
