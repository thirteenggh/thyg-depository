package org.sonatype.nexus.common.entity

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

/**
 * Tests for {@link DetachedEntityId}
 */
class DetachedEntityIdTest
    extends TestSupport
{
  @Test
  void 'detached equality'() {
    DetachedEntityId a = new DetachedEntityId('a')
    assert a.equals(a)
    assert a.equals(new DetachedEntityId('a'))

    DetachedEntityId b = new DetachedEntityId('b')
    assert !a.equals(b)
  }
}
