package org.sonatype.nexus.repository.view.matchers.token;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link Token}.
 */
public class TokenTest
    extends TestSupport
{
  @Test
  public void literalToken() {
    checkRegexpForm("/", "\\Q/\\E");
    checkRegexpForm("/--a", "\\Q/--a\\E");
    checkRegexpForm("[]", "\\Q[]\\E");
  }

  private void checkRegexpForm(final String token, final String regexp) {
    assertThat(new LiteralToken(token).toRegexp(), is(equalTo(regexp)));
  }
}
