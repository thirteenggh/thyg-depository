package org.sonatype.nexus.testcommon.matchers;

import java.time.OffsetDateTime;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.DateTime;

public class NexusMatchers
{
  /**
   * Matches the DateTime specified ignoring chronology
   */
  public static Matcher<DateTime> time(final DateTime dateTime) {
    return new TypeSafeMatcher<DateTime>()
    {
      @Override
      public void describeTo(final Description description) {
        description.appendText("a DateTime ").appendText(" ").appendValue(dateTime.toString());
      }

      @Override
      protected boolean matchesSafely(final DateTime item) {
        return dateTime.isEqual(item);
      }
    };
  }

  /**
   * Matches the OffsetDateTime specified ignoring chronology
   */
  public static Matcher<OffsetDateTime> time(final OffsetDateTime dateTime) {
    return new TypeSafeMatcher<OffsetDateTime>()
    {
      @Override
      public void describeTo(final Description description) {
        description.appendText("an OffsetDateTime ").appendText(" ").appendValue(dateTime.toString());
      }

      @Override
      protected boolean matchesSafely(final OffsetDateTime item) {
        return dateTime.isEqual(item);
      }
    };
  }
}
