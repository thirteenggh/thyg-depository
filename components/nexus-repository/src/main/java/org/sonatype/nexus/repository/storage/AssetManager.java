package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.capability.GlobalRepositorySettings;

import com.google.common.annotations.VisibleForTesting;
import org.joda.time.Duration;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.time.DateHelper.toJavaDuration;
import static org.sonatype.nexus.common.time.DateHelper.toJodaDuration;

/**
 * Responsible for altering the runtime behaviour of assets
 *
 * @since 3.16
 */
@Named
@Singleton
public class AssetManager
    extends ComponentSupport
{
  public static final Duration DEFAULT_LAST_DOWNLOADED_INTERVAL =
      toJodaDuration(GlobalRepositorySettings.DEFAULT_LAST_DOWNLOADED_INTERVAL);

  private final GlobalRepositorySettings globalSettings;

  @Inject
  public AssetManager(final GlobalRepositorySettings globalSettings) {
    this.globalSettings = checkNotNull(globalSettings);
  }

  public void setLastDownloadedInterval(final Duration lastDownloadedInterval) {
    globalSettings.setLastDownloadedInterval(toJavaDuration(lastDownloadedInterval));
  }

  @VisibleForTesting
  public Duration getLastDownloadedInterval() {
    return toJodaDuration(globalSettings.getLastDownloadedInterval());
  }

  public boolean maybeUpdateLastDownloaded(final Asset asset) {
    return asset.markAsDownloaded(getLastDownloadedInterval());
  }
}
