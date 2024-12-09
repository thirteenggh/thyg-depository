package org.sonatype.nexus.repository.view.matchers.logic

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Request
import org.sonatype.nexus.repository.view.matchers.RegexMatcher

import org.junit.Test

import static org.mockito.Mockito.mock

/**
 * Tests for {@link AndMatcher}.
 */
class AndMatcherTest
  extends TestSupport
{
  private Context context(String path) {
    return new Context(mock(Repository.class), new Request.Builder().action('GET').path(path).build())
  }

  @Test
  void 'AND 2 matchers'() {
    def underTest = new AndMatcher([
        new RegexMatcher('foo.*'),
        new RegexMatcher('.*bar')
    ])

    assert underTest.matches(context('foobar'))
    assert underTest.matches(context('fooooooobar'))
    assert !underTest.matches(context('foobarbaz'))
  }
}
