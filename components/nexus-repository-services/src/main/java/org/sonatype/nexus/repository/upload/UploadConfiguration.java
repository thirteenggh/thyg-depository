package org.sonatype.nexus.repository.upload;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @since 3.7
 */
public class UploadConfiguration
{
  /**
   * Configuration property for enabling the upload ui
   */
  public static final String ENABLED = "nexus.upload.component.enabled";

  private final boolean enabled;

  @Inject
  public UploadConfiguration(@Named("${" + ENABLED + ":-true}") final boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
