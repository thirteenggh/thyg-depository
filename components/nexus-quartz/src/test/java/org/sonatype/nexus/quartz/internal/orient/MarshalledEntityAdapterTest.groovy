package org.sonatype.nexus.quartz.internal.orient

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.entity.EntityHelper
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static org.hamcrest.Matchers.startsWith

/**
 * Tests for {@link MarshalledEntityAdapter}.
 */
class MarshalledEntityAdapterTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory('test')

  @Rule
  public ExpectedException expected = ExpectedException.none()

  private MarshalledEntityAdapter underTest

  @Before
  void setUp() {
    underTest = new TestMarshalledEntityAdapter()

    database.instance.connect().withCloseable {db ->
      underTest.register(db)

      // disable validation of these fields so we corrupt them later on
      def schema = db.metadata.schema.getClass('test_marshalled_entity')
      schema.getProperty('value_type').notNull = false
      schema.getProperty('value_data').notNull = false
    }
  }

  @Test
  void 'missing value_type throws clear exception'() {
    def entity = new TestMarshalledEntity()
    entity.setValue(new TestMarshalledValue())

    // persist and corrupt data
    database.instance.connect().withCloseable {db ->
      underTest.addEntity(db, entity).field('value_type', null).save()
    }

    expected.expect(IllegalStateException)
    expected.expectMessage(startsWith('Marshalled document missing value_type: '))

    // attempt to restore corrupted data
    database.instance.connect().withCloseable {db ->
      underTest.read(db, EntityHelper.id(entity))
    }
  }


  @Test
  void 'missing value_data throws clear exception'() {
    def entity = new TestMarshalledEntity()
    entity.setValue(new TestMarshalledValue())

    // persist and corrupt data
    database.instance.connect().withCloseable {db ->
      underTest.addEntity(db, entity).field('value_data', null).save()
    }

    expected.expect(IllegalStateException)
    expected.expectMessage(startsWith('Marshalled document missing value_data: '))

    // attempt to restore corrupted data
    database.instance.connect().withCloseable {db ->
      underTest.read(db, EntityHelper.id(entity))
    }
  }
}
