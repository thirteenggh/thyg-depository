package org.sonatype.nexus.common.entity

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

import static org.junit.Assert.fail

/**
 * Tests for {@link EntityHelper}
 */
class EntityHelperTest
    extends TestSupport
{
  @Test
  void 'entity with-out metadata'() {
    def entity = new AbstractEntity() {}
    assert !EntityHelper.hasMetadata(entity)

    try {
      EntityHelper.metadata(entity)
      fail()
    }
    catch (IllegalStateException e) {
      // expected
    }

    try {
      EntityHelper.isDetached(entity)
      fail()
    }
    catch (IllegalStateException e) {
      // expected
    }

    try {
      EntityHelper.id(entity)
      fail()
    }
    catch (IllegalStateException e) {
      // expected
    }

    try {
      EntityHelper.version(entity)
      fail()
    }
    catch (IllegalStateException e) {
      // expected
    }
  }

  @Test
  void 'entity with metadata'() {
    def entity = new AbstractEntity() {}
    entity.setEntityMetadata(new DetachedEntityMetadata(new DetachedEntityId('a'), new DetachedEntityVersion('1')))

    assert EntityHelper.hasMetadata(entity)
    assert EntityHelper.metadata(entity) != null
    assert EntityHelper.isDetached(entity)
    assert EntityHelper.id(entity).value == 'a'
    assert EntityHelper.version(entity).value == '1'
  }
}
