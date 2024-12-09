package org.sonatype.nexus.repository.upgrade

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.orient.OClassNameBuilder
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule

import com.orientechnologies.orient.core.metadata.schema.OSchema
import com.orientechnologies.orient.core.record.impl.ODocument
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import static org.hamcrest.CoreMatchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Tests {@link ConfigDatabaseUpgrade_1_3} with an in-memory database.
 */
class ConfigDatabaseUpgrade_1_3_Test
    extends TestSupport
{
  private static final String QUARTZ_JOB_DETAIL_CLASS = new OClassNameBuilder()
      .prefix("quartz")
      .type("job_detail")
      .build()

  @Rule
  public DatabaseInstanceRule configDatabase = DatabaseInstanceRule.inMemory("test_config")

  ConfigDatabaseUpgrade_1_3 underTest

  @Before
  void setUp() {
    configDatabase.instance.connect().withCloseable { db ->
      OSchema schema = db.metadata.schema
      schema.createClass(QUARTZ_JOB_DETAIL_CLASS)
      job('restore', 'blobstore.rebuildComponentDB')
      job('not-restore', 'something.notRestore')
    }

    underTest = new ConfigDatabaseUpgrade_1_3(configDatabase.instanceProvider)
  }

  @Test
  void 'when upgrading once'() {
    underTest.apply()

    assertUpdates()
  }

  @Test
  void 'when upgrading twice'() {
    underTest.apply()

    underTest.apply()

    assertUpdates()
  }

  private Object assertUpdates() {
    configDatabase.instance.connect().withCloseable { db ->
      def updated = db.browseClass(QUARTZ_JOB_DETAIL_CLASS).findResults { job ->
        def value = job.field('value_data')
        if ('blobstore.rebuildComponentDB' == value['jobDataMap']['.typeId']) {
          assertThat(value['jobDataMap']['restoreBlobs'] as String, equalTo('true'))
          return value['jobDataMap']['.name']
        }
        return null
      }
      assertThat(updated as List<String>, equalTo(['restore']))
    }
  }

  private static job(final String name, final String typeId) {
    ODocument job = new ODocument(QUARTZ_JOB_DETAIL_CLASS)
    job.field('value_data', [jobDataMap: ['.name': name, '.typeId': typeId]])
    job.save()
  }
}
