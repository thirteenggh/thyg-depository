package com.sonatype.nexus.edition.oss;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationLicense;

/**
 * OSS {@link ApplicationLicense}.
 *
 * @since 3.0
 */
@Named("OSS")
@Singleton
public class ApplicationLicenseImpl
  implements ApplicationLicense
{
  /**
   * Always {@code false}.
   */
  @Override
  public boolean isRequired() {
    return false;
  }

  /**
   * Always {@code false}.
   */
  @Override
  public boolean isValid() {
    return false;
  }

  /**
   * Always {@code false}.
   */
  @Override
  public boolean isInstalled() {
    return false;
  }

  /**
   * Always {@code false}.
   */
  @Override
  public boolean isExpired() {
    return false;
  }

  /**
   * Always empty-map.
   */
  @Override
  public Map<String, Object> getAttributes() {
    return Collections.emptyMap();
  }

  /**
   * Always {@code null}.
   */
  @Override
  @Nullable
  public String getFingerprint() {
    return null;
  }

  @Override
  public void refresh() {
    // no-op
  }
}
