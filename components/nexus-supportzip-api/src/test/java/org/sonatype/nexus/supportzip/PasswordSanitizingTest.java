package org.sonatype.nexus.supportzip;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.sonatype.nexus.supportzip.PasswordSanitizing.REPLACEMENT;

/**
 * Tests for {@link PasswordSanitizing}
 */
public class PasswordSanitizingTest
{
  @Test
  public void testSanitizingSensitiveData() {
    PasswordSanitizing<Map<String, String>> passwordSanitizing = new PasswordSanitizing<>();
    Map<String, String> sensitiveData = ImmutableMap.of(
        "password", "admin123",
        "secret", "admin123",
        "bearerToken", "admin123");
    Map<String, String> sanitized = passwordSanitizing.transform(sensitiveData);
    assertNotNull(sanitized);
    assertThat(sanitized.toString(), not(containsString("admin123")));
  }

  @Test
  public void testPlainDataIsNotChanged() {
    PasswordSanitizing<Map<String, String>> passwordSanitizing = new PasswordSanitizing<>();
    Map<String, String> sensitiveData = ImmutableMap.of(
        "name", "John",
        "lastName", "Doe",
        "password", "admin123");
    Map<String, String> sanitized = passwordSanitizing.transform(sensitiveData);
    assertNotNull(sanitized);
    Map<String, String> expected = ImmutableMap.of(
        "name", "John",
        "lastName", "Doe",
        "password", REPLACEMENT);
    assertThat(sanitized, is(expected));
  }
}
