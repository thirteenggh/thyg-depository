package org.sonatype.nexus.blobstore.s3.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.log.LogConfigurationCustomizer;
import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * Configures S3 loggers.
 *
 * @since 3.14
 */
@Singleton
@Named
public class S3LogConfigurationCustomizer
    implements LogConfigurationCustomizer
{

  @Override
  public void customize(final Configuration configuration) {
    // limit noisy S3 logger
    configuration.setLoggerLevel("com.amazonaws.services.s3.internal.S3AbortableInputStream", LoggerLevel.ERROR);
  }
}
