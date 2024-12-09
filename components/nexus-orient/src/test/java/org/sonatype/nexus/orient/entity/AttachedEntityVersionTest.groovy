package org.sonatype.nexus.orient.entity

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.entity.DetachedEntityVersion
import org.sonatype.nexus.common.entity.EntityVersion

import com.orientechnologies.orient.core.metadata.schema.OClass
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Tests for {@link AttachedEntityVersion}
 */
class AttachedEntityVersionTest
    extends TestSupport
{
  @Mock
  EntityAdapter entityAdapter

  @Before
  void setUp() {
    when(entityAdapter.schemaType).thenReturn(mock(OClass.class, 'oclass'))
  }

  @Test
  void 'human representation'() {
    AttachedEntityVersion a = new AttachedEntityVersion(entityAdapter, 1)
    def str = a.toString()
    println str
    assert str != null
  }

  @Test
  void 'value externalization'() {
    AttachedEntityVersion a = new AttachedEntityVersion(entityAdapter, 1)
    println a.value
    assert a.value != null
  }

  @Test
  void 'attached equality'() {
    AttachedEntityVersion a = new AttachedEntityVersion(entityAdapter, 1)
    assert a.equals(a)
    assert a.equals(new AttachedEntityVersion(entityAdapter, 1))

    AttachedEntityVersion b = new AttachedEntityVersion(entityAdapter, 2)
    assert !a.equals(b)
    assert !b.equals(a)
  }

  @SuppressWarnings("GrEqualsBetweenInconvertibleTypes")
  @Test
  void 'detached equality'() {
    EntityVersion a = new AttachedEntityVersion(entityAdapter, 1)
    EntityVersion b = new DetachedEntityVersion('1')
    assert a.equals(b)
    assert b.equals(a)

    EntityVersion c = new DetachedEntityVersion('2')
    assert !a.equals(c)
    assert !c.equals(a)
  }
}
