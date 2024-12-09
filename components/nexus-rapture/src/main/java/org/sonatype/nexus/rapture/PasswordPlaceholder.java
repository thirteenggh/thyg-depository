package org.sonatype.nexus.rapture;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;

/**
 * Helper for password-placeholder data.
 *
 * @since 3.0
 */
public class PasswordPlaceholder
{
  private PasswordPlaceholder() {
    // empty
  }

  /**
   * Token used for passwords that are defined, but which are not transmitted.
   */
  @VisibleForTesting
  static final String VALUE = "#~NEXUS~PLACEHOLDER~PASSWORD~#";

  /**
   * Returns password placeholder.
   */
  public static String get() {
    return VALUE;
  }

  /**
   * Returns fake password placeholder unless value is {@code null}.
   */
  @Nullable
  public static String get(@Nullable final String value) {
    if (value != null) {
      return VALUE;
    }
    return null;
  }

  /**
   * Determine if given value is a password placeholder.
   */
  public static boolean is(@Nullable final String value) {
    return VALUE.equals(value);
  }
}
