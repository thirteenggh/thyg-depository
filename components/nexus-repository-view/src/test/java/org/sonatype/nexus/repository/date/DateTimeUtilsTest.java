package org.sonatype.nexus.repository.date;

import java.util.regex.Pattern;

import org.sonatype.goodies.testsupport.TestSupport;

import org.joda.time.DateTime;
import org.junit.Test;

import static java.util.regex.Pattern.compile;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.sonatype.nexus.repository.date.DateTimeUtils.formatDateTime;

public class DateTimeUtilsTest
    extends TestSupport
{
  private Pattern rfc1123 = compile("[a-zA-Z]{3}, \\d{2} [a-zA-Z]{3} \\d{4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]{3}");

  @Test
  public void format_DateTime_Uses_RFC1123_Pattern() {
    assertTrue(rfc1123.matcher(formatDateTime(new DateTime())).matches());
  }

  @Test
  public void format_DateTime_Uses_GMT_TimeZone() {
    assertThat(formatDateTime(new DateTime()), endsWith("GMT"));
  }
}
