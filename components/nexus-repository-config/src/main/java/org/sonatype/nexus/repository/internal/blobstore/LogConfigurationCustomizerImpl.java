package org.sonatype.nexus.repository.internal.blobstore;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;

import static org.sonatype.nexus.common.log.LoggerLevel.DEFAULT;

/**
 * Blob-store {@link LogConfigurationCustomizer}.
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
    config.setLoggerLevel("org.sonatype.nexus.blobstore", DEFAULT);
    config.setLoggerLevel("org.sonatype.nexus.blobstore.iostat", DEFAULT);
    config.setLoggerLevel("org.sonatype.nexus.repository.internal.blobstore", DEFAULT);
  }
}
