package org.sonatype.nexus.common.entity

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

/**
 * Tests for {@link DetachedEntityVersion}
 */
class DetachedEntityVersionTest
    extends TestSupport
{
  @Test
  void 'detached equality'() {
    DetachedEntityVersion a = new DetachedEntityVersion('a')
    assert a.equals(a)
    assert a.equals(new DetachedEntityVersion('a'))

    DetachedEntityVersion b = new DetachedEntityVersion('b')
    assert !a.equals(b)
  }
}
