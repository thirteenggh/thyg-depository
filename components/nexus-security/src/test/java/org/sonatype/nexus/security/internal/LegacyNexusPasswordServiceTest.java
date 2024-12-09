package org.sonatype.nexus.security.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link LegacyNexusPasswordService}.
 */
public class LegacyNexusPasswordServiceTest
    extends TestSupport
{
  private LegacyNexusPasswordService underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new LegacyNexusPasswordService();
  }

  @Test
  public void testSha1Hash() {
    String password = "admin123";
    String sha1Hash = "f865b53623b121fd34ee5426c792e5c33af8c227";

    assertThat(underTest.passwordsMatch(password, sha1Hash), is(true));
  }

  @Test
  public void testMd5Hash() {
    String password = "admin123";
    String md5Hash = "0192023a7bbd73250516f069df18b500";

    assertThat(underTest.passwordsMatch(password, md5Hash), is(true));
  }

  @Test
  public void testInvalidSha1Hash() {
    String password = "admin123";
    String sha1Hash = "f865b53623b121fd34ee5426c792e5c33af8c228";

    assertThat(underTest.passwordsMatch(password, sha1Hash), is(false));
  }

  @Test
  public void testInvalidMd5Hash() {
    String password = "admin123";
    String md5Hash = "0192023a7bbd73250516f069df18b501";

    assertThat(underTest.passwordsMatch(password, md5Hash), is(false));
  }
}
