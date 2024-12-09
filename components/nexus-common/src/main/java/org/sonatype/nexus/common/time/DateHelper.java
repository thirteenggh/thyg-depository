package org.sonatype.nexus.common.time;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Helper for {@link DateTime} and {@link Date} types.
 *
 * @since 3.0
 */
public class DateHelper
{
  private DateHelper() {
    // empty
  }

  /**
   * Copy given date.
   */
  @Nullable
  public static Date copy(@Nullable final Date date) {
    if (date != null) {
      return new Date(date.getTime());
    }
    return null;
  }

  /**
   * Converts input {@link Date} to {@link DateTime}, or {@code null} if input is {@code null}.
   */
  @Nullable
  public static DateTime toDateTime(@Nullable final Date date) {
    if (date == null) {
      return null;
    }
    return new DateTime(date.getTime());
  }

  /**
   * Converts input {@link DateTime} to {@link Date}, or {@code null} if input is {@code null}.
   */
  @Nullable
  public static Date toDate(@Nullable final DateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.toDate();
  }

  /**
   * @since 3.25
   */
  public static DateTime toDateTime(final OffsetDateTime offsetDateTime) {
    return new DateTime(offsetDateTime.toInstant().toEpochMilli(), DateTimeZone.UTC);
  }

  /**
   * @since 3.25
   */
  public static OffsetDateTime toOffsetDateTime(final DateTime dateTime) {
    return Instant.ofEpochMilli(dateTime.getMillis()).atOffset(ZoneOffset.UTC);
  }

  /**
   * @since 3.24
   */
  public static Duration toJavaDuration(final org.joda.time.Duration jodaDuration) {
    return Duration.ofMillis(jodaDuration.getMillis());
  }

  /**
   * @since 3.24
   */
  public static org.joda.time.Duration toJodaDuration(final Duration javaDuration) {
    return org.joda.time.Duration.millis(javaDuration.toMillis());
  }
}
