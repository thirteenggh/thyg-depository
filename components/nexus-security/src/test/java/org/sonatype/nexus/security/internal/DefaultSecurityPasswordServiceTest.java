package org.sonatype.nexus.security.internal;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.shiro.crypto.hash.Hash;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link DefaultSecurityPasswordService}.
 */
public class DefaultSecurityPasswordServiceTest
    extends TestSupport
{
  private DefaultSecurityPasswordService underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new DefaultSecurityPasswordService(new LegacyNexusPasswordService());
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
  public void testShiro1HashFormat() {
    String password = "admin123";
    String shiro1Hash = "$shiro1$SHA-512$1024$zjU1u+Zg9UNwuB+HEawvtA==$IzF/OWzJxrqvB5FCe/2+UcZhhZYM2pTu0TEz7Ybnk65AbbEdUk9ntdtBzkN8P3gZby2qz6MHKqAe8Cjai9c4Gg==";

    assertThat(underTest.passwordsMatch(password, shiro1Hash), is(true));
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

  @Test
  public void testInvalidShiro1HashFormat() {
    String password = "admin123";
    String shiro1Hash = "$shiro1$SHA-512$1024$zjU1u+Zg9UNwuB+HEawvtA==$IzF/OWzjxrqvB5FCe/2+UcZhhZYM2pTu0TEz7Ybnk65AbbEdUk9ntdtBzkN8P3gZby2qz6MHKqAe8Cjai9c4Gg==";

    assertThat(underTest.passwordsMatch(password, shiro1Hash), is(false));
  }

  @Test
  public void testHash() {
    String password = "testpassword";
    Hash hash = underTest.hashPassword(password);

    assertThat(underTest.passwordsMatch(password, hash), is(true));
  }
}
