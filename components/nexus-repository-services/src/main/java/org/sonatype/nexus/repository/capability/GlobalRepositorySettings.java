package org.sonatype.nexus.repository.capability;

import java.time.Duration;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;

/**
 * Global repository settings.
 *
 * @since 3.24
 */
@Named
@Singleton
public class GlobalRepositorySettings
    extends ComponentSupport
{
  public static final Duration DEFAULT_LAST_DOWNLOADED_INTERVAL = Duration.ofHours(12);

  private Duration lastDownloadedInterval = DEFAULT_LAST_DOWNLOADED_INTERVAL;

  public void setLastDownloadedInterval(final Duration lastDownloadedInterval) {
    if (lastDownloadedInterval.toHours() < 1) {
      log.warn("A lastDownloaded interval of {} seconds has been configured, a value less than"
          + " 1 hour is not recommended for performance reasons", lastDownloadedInterval.getSeconds());
    }
    this.lastDownloadedInterval = lastDownloadedInterval;
  }

  public Duration getLastDownloadedInterval() {
    return lastDownloadedInterval;
  }
}
