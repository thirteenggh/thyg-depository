package org.sonatype.nexus.common.property;

import org.sonatype.goodies.common.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper to retrieve a system property typed value.
 */
public class SystemPropertiesHelper
{
  private static final Logger log = LoggerFactory.getLogger(SystemPropertiesHelper.class);

  private SystemPropertiesHelper() {
    // empty
  }

  public static int getInteger(final String key, final int defaultValue) {
    final String value = System.getProperty(key);

    if (value == null || value.trim().length() == 0) {
      return defaultValue;
    }

    try {
      return Integer.valueOf(value);
    }
    catch (NumberFormatException e) { // NOSONAR
      log.warn("Invalid integer '{}' for property '{}'. Defaulting to '{}'", value, key, defaultValue);
      return defaultValue;
    }
  }

  public static long getLong(final String key, final long defaultValue) {
    final String value = System.getProperty(key);

    if (value == null || value.trim().length() == 0) {
      return defaultValue;
    }

    try {
      return Long.valueOf(value);
    }
    catch (NumberFormatException e) { // NOSONAR
      log.warn("Invalid long '{}' for property '{}'. Defaulting to '{}'", value, key, defaultValue);
      return defaultValue;
    }
  }

  public static boolean getBoolean(final String key, final boolean defaultValue) {
    final String value = System.getProperty(key);

    if (value == null || value.trim().length() == 0) {
      return defaultValue;
    }

    return Boolean.valueOf(value);
  }

  public static String getString(final String key, final String defaultValue) {
    return System.getProperty(key, defaultValue);
  }

  /**
   * @since 3.16
   */
  public static Time getTime(final String key, final Time defaultValue) {
    final String value = System.getProperty(key);

    if (value == null || value.trim().length() == 0) {
      return defaultValue;
    }

    try {
      return Time.parse(value);
    }
    catch (RuntimeException e) { // NOSONAR
      log.warn("Invalid time '{}' for property '{}'. Defaulting to '{}'", value, key, defaultValue);
      return defaultValue;
    }
  }
}
