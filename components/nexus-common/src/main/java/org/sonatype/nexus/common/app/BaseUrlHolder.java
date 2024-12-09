package org.sonatype.nexus.common.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Helper to hold the calculated base URL of the current request.
 *
 * @since 2.8
 */
public final class BaseUrlHolder
{
  private static final Logger log = LoggerFactory.getLogger(BaseUrlHolder.class);

  private static final InheritableThreadLocal<String> value = new InheritableThreadLocal<>();

  private BaseUrlHolder() {
    // empty
  }

  /**
   * Set the current base URL.
   *
   * The value will be normalized to never end with "/".
   */
  public static void set(String url) {
    checkNotNull(url);

    // strip off trailing "/", note this is done so that script/template can easily $baseUrl/foo
    if (url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }

    log.trace("Set: {}", url);
    value.set(url);
  }

  /**
   * Returns the current base URL; never null.
   *
   * @throws IllegalStateException
   */
  public static String get() {
    String url = value.get();
    checkState(url != null, "Base URL not set");
    return url;
  }

  public static void unset() {
    log.trace("Unset");
    value.remove();
  }

  public static boolean isSet() {
    return value.get() != null;
  }
}
