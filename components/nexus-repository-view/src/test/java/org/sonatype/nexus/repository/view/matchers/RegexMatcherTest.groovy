package org.sonatype.nexus.repository.view.matchers

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Request

import org.junit.Test

import static org.mockito.Mockito.mock

/**
 * Tests for {@link RegexMatcher}.
 */
class RegexMatcherTest
  extends TestSupport
{
  private Context context(String path) {
    return new Context(mock(Repository.class), new Request.Builder().action('GET').path(path).build())
  }

  @Test
  void 'basic'() {
    def underTest = new RegexMatcher('foo.*bar')
    assert underTest.matches(context('foobar'))
    assert underTest.matches(context('fooooooobar'))
    assert !underTest.matches(context('foobarbaz'))
  }
}
