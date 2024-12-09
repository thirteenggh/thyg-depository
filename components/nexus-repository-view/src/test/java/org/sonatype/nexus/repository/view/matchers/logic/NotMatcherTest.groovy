package org.sonatype.nexus.repository.view.matchers.logic

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Request
import org.sonatype.nexus.repository.view.matchers.LiteralMatcher

import org.junit.Test

import static org.mockito.Mockito.mock

/**
 * Tests for {@link NotMatcher}.
 */
class NotMatcherTest
  extends TestSupport
{
  private Context context(String path) {
    return new Context(mock(Repository.class), new Request.Builder().action('GET').path(path).build())
  }

  @Test
  void 'NOT 2 matchers'() {
    def underTest = new NotMatcher(
        new LiteralMatcher('foo')
    )

    assert !underTest.matches(context('foo'))
    assert underTest.matches(context('bar'))
  }
}
