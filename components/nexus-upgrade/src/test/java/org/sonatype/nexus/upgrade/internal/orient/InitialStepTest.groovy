package org.sonatype.nexus.upgrade.internal.orient

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Before
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is

/**
 * Test for {@link InitialStep}
 */
class InitialStepTest
    extends TestSupport
{
  InitialStep underTest

  @Before
  public void setup() {
    underTest = new InitialStep([config: '1.2'])
  }

  @Test
  public void 'initial step satisfies dependency with exact version match'() {
    assertThat(underTest.satisfies('config', '1.2'), is(true))
  }

  @Test
  public void 'initial step does not satisfy dependency if initial version lower than the checked version'() {
    assertThat(underTest.satisfies('config', '2.0'), is(false))
  }

  @Test
  public void ' step does not satisfy the dependency if the provided model does not exist and version is over 1.0'() {
    assertThat(underTest.satisfies('foo', '2.0'), is(false))
  }

  @Test
  public void 'the initial step does satisfy the dependency if the existing version is greater than required'() {
    assertThat(underTest.satisfies('config', '1.1'), is(true))
  }
}
