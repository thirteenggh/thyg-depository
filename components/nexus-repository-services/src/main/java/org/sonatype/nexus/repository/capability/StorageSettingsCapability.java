package org.sonatype.nexus.repository.capability;

import java.time.Duration;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.capability.CapabilitySupport;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Long.parseLong;
import static java.time.Duration.ofHours;
import static org.sonatype.nexus.repository.capability.GlobalRepositorySettings.DEFAULT_LAST_DOWNLOADED_INTERVAL;

/**
 * Storage settings capability.
 *
 * @since 3.16
 */
@Named(StorageSettingsCapabilityDescriptor.TYPE_ID)
public class StorageSettingsCapability
    extends CapabilitySupport<StorageSettingsCapabilityConfiguration>
{
  private final GlobalRepositorySettings globalSettings;

  @Inject
  public StorageSettingsCapability(final GlobalRepositorySettings globalSettings) {
    this.globalSettings = checkNotNull(globalSettings);
  }

  @Override
  protected StorageSettingsCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new StorageSettingsCapabilityConfiguration(properties);
  }

  @Override
  protected void onUpdate(final StorageSettingsCapabilityConfiguration config) throws Exception {
    if (context().isActive()) {
      configureDownloadedInterval(config);
    }
  }

  @Override
  protected void onActivate(final StorageSettingsCapabilityConfiguration config) throws Exception {
    configureDownloadedInterval(config);
  }

  @Override
  protected void onPassivate(final StorageSettingsCapabilityConfiguration config) throws Exception {
    resetDownloadedInterval();
  }

  private void configureDownloadedInterval(final StorageSettingsCapabilityConfiguration config) {
    log.info("Using configured value of {} hours for LastDownloaded interval", config.getLastDownloadedInterval());
    globalSettings.setLastDownloadedInterval(parseAsHours(config.getLastDownloadedInterval()));
  }

  private void resetDownloadedInterval() {
    log.info("Reverting back to {} hours for LastDownloaded interval",
        DEFAULT_LAST_DOWNLOADED_INTERVAL.toHours());
    globalSettings.setLastDownloadedInterval(DEFAULT_LAST_DOWNLOADED_INTERVAL);
  }

  private Duration parseAsHours(final String hours) {
    return ofHours(parseLong(hours));
  }
}
