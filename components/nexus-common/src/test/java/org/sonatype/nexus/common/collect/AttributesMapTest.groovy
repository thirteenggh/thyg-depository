package org.sonatype.nexus.common.collect

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Before
import org.junit.Test

import static org.junit.Assert.fail

/**
 * Tests for {@link org.sonatype.nexus.common.collect.AttributesMap}.
 */
class AttributesMapTest
  extends TestSupport
{
  private AttributesMap underTest

  @Before
  void setUp() {
    underTest = new AttributesMap()
  }

  @Test
  void 'set null removes'() {
    underTest.set('foo', 'bar')
    underTest.set('foo', null)
    assert !underTest.contains('foo')
  }

  @Test
  void 'required value'() {
    underTest.set('foo', 'bar')
    def value = underTest.require('foo')
    assert value == 'bar'

    try {
      underTest.require('baz')
      fail()
    }
    catch (Exception e) {
      // expected
    }
  }

  @Test
  void 'contains value'() {
    underTest.set('foo', 'bar')
    assert underTest.contains('foo')
    assert !underTest.contains('baz')
  }

  @Test
  void 'size empty and clear'() {
    assert underTest.isEmpty()
    assert underTest.size() == 0

    underTest.set('foo', 'bar')
    log underTest
    assert underTest.size() == 1

    underTest.set('baz', 'ick')
    log underTest
    assert underTest.size() == 2

    underTest.clear()
    log underTest
    assert underTest.isEmpty()
    assert underTest.size() == 0
  }

  @Test
  void 'typed accessors'() {
    def value

    underTest.set('foo', 1)
    value = underTest.get('foo', Integer.class)
    assert value.class == Integer.class
    assert value == 1

    underTest.set('foo', false)
    value = underTest.get('foo', Boolean.class)
    assert value.class == Boolean.class
    assert value == false
  }

  // private to test creation with accessible=true
  private static class GetOrCreateAttribute
  {
    // empty
  }

  @Test
  void 'get or create'() {
    assert !underTest.contains(GetOrCreateAttribute.class)
    def value = underTest.getOrCreate(GetOrCreateAttribute.class)
    assert value != null
    assert underTest.contains(GetOrCreateAttribute.class)
  }

  @Test
  void 'handle Date as Date or Long'() {
    Date testDate = new Date()
    Long testDateLong = testDate.getTime()

    underTest.set('foo', testDate)
    underTest.set('bar', testDateLong)

    assert underTest.get('foo', Date.class) == testDate
    assert underTest.get('bar', Date.class) == testDate
  }

  @Test
  void 'compute'() {
    def result

    result = underTest.compute('foo', { v -> v ? v + 1 : 1 })
    assert result == null
    assert underTest.get('foo') == 1
    result = underTest.compute('foo', { v -> v ? v + 1 : 1 })
    assert result == 1
    assert underTest.get('foo') == 2
    result = underTest.compute('foo', { v -> v ? v + 1 : 1 })
    assert result == 2
    assert underTest.get('foo') == 3
    result = underTest.compute('foo', { v -> v ? v + 1 : 1 })
    assert result == 3
    assert underTest.get('foo') == 4
  }
}
