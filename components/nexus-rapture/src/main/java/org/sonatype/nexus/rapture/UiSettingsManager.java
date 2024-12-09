package org.sonatype.nexus.rapture;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rapture.internal.settings.RaptureSettings;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Rapture.
 *
 * @since 3.0
 */
@Named
@Singleton
public class UiSettingsManager
    extends ComponentSupport
{
  private RaptureSettings settings = new RaptureSettings();

  public RaptureSettings getSettings() {
    return settings;
  }

  public void setSettings(final RaptureSettings settings) {
    this.settings = checkNotNull(settings);
  }

  public void resetSettings() {
    this.settings = new RaptureSettings();
  }
}
