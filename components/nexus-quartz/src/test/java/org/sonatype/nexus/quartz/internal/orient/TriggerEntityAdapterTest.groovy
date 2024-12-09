package org.sonatype.nexus.quartz.internal.orient

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.entity.EntityHelper
import org.sonatype.nexus.orient.entity.AttachedEntityHelper
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.quartz.impl.triggers.CronTriggerImpl

/**
 * Tests for {@link TriggerEntityAdapter}.
 */
class TriggerEntityAdapterTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory('test')

  private TriggerEntityAdapter underTest

  @Before
  void setUp() {
    underTest = new TriggerEntityAdapter()

    database.instance.connect().withCloseable {db ->
      underTest.register(db)
    }
  }

  @Test
  void 'add CronTriggerImpl'() {
    def trigger1 = new CronTriggerImpl(
        name: 'test-name',
        group: 'test-group',
        jobName: 'test-job-name',
        jobGroup: 'test-job-group',
        cronExpression: '0 0 10 * * ?'
    )
    println trigger1

    def entity1 = new TriggerEntity(trigger1, TriggerEntity.State.WAITING)
    println entity1

    // verify setting value populates some entity bits
    assert entity1.name == trigger1.name
    assert entity1.group == trigger1.group
    assert entity1.jobName == trigger1.jobName
    assert entity1.jobGroup == trigger1.jobGroup

    // add the entity
    database.instance.acquire().withCloseable {db ->
      underTest.addEntity(db, entity1)

      def doc = AttachedEntityHelper.document(entity1)
      println doc.toJSON("prettyPrint")
    }

    // extract the id
    def id1 = EntityHelper.id(entity1)
    println id1

    // lookup the entity and verify its contents
    database.instance.acquire().withCloseable {db ->
      def entity2 = underTest.read(db, id1)
      println entity2

      assert entity2.name == entity1.name
      assert entity2.group == entity1.group
      assert entity2.state == TriggerEntity.State.WAITING
      assert entity2.value != null
      assert entity2.value instanceof CronTriggerImpl

      CronTriggerImpl trigger2 = (CronTriggerImpl)entity2.value
      println trigger2

      assert trigger2.key == trigger1.key
      assert trigger2.name == trigger1.name
      assert trigger2.group == trigger1.group
      assert trigger2.jobName == trigger1.jobName
      assert trigger2.jobGroup == trigger1.jobGroup
      assert trigger2.jobKey == trigger1.jobKey
      assert trigger2.cronExpression == trigger1.cronExpression
    }
  }

  // TODO: Add tests for: readByKey, existsByKey, browseGroups, browseByCalendarName,
  // TODO: ... browseWithPredicate, deleteByKey, deleteAll and browseByJobKey

  // TODO: Verify other default trigger impls for marshalling sanity:
  //    CalendarIntervalTriggerImpl
  //    DailyTimeIntervalTriggerImpl
  //    SimpleTriggerImpl
}
