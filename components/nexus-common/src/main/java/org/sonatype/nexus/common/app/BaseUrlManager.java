package org.sonatype.nexus.common.app;

import javax.annotation.Nullable;

/**
 * Base-URL manager.
 *
 * @since 3.0
 */
public interface BaseUrlManager
{
  void setUrl(String url);

  String getUrl();

  boolean isForce();

  void setForce(boolean force);

  /**
   * Detect base-URL from current environment.
   */
  @Nullable
  String detectUrl();

  /**
   * Detect base-URL and register with {@link BaseUrlHolder} if non-null.
   */
  void detectAndHoldUrl();
}
