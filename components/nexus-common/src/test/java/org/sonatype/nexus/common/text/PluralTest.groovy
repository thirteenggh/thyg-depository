package org.sonatype.nexus.common.text

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

/**
 * Tests for {@link Plural}
 */
class PluralTest
  extends TestSupport
{
  @Test
  void 'simple plural'() {
    assert Plural.of(-1, 'dog') == '-1 dogs'
    assert Plural.of(0, 'dog') == '0 dogs'
    assert Plural.of(1, 'dog') == '1 dog'
    assert Plural.of(2, 'dog') == '2 dogs'
  }

  @Test
  void 'complex plural'() {
    assert Plural.of(-1, 'candy', 'candies') == '-1 candies'
    assert Plural.of(0, 'candy', 'candies') == '0 candies'
    assert Plural.of(1, 'candy', 'candies') == '1 candy'
    assert Plural.of(2, 'candy', 'candies') == '2 candies'
  }
}
