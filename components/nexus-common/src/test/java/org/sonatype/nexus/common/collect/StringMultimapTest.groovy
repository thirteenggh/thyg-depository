package org.sonatype.nexus.common.collect

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Before
import org.junit.Test

/**
 * Tests for {@link StringMultimap}.
 */
class StringMultimapTest
  extends TestSupport
{
  private StringMultimap underTest

  @Before
  void setUp() {
    underTest = new StringMultimap()
  }

  @Test
  void 'size validation'() {
    log underTest
    assert underTest.size() == 0

    underTest.set('foo', 'bar')
    log underTest
    assert underTest.size() == 1

    underTest.set('foo', 'baz')
    log underTest
    assert underTest.size() == 1

    underTest.set('ick', 'poo')
    log underTest
    assert underTest.size() == 2
  }
}
