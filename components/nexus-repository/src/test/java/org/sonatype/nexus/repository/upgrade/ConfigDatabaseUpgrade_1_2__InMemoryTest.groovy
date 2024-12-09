package org.sonatype.nexus.repository.upgrade

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.orient.OClassNameBuilder
import org.sonatype.nexus.orient.OIndexNameBuilder
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule
import org.sonatype.nexus.security.PasswordHelper

import com.google.common.annotations.VisibleForTesting
import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE
import com.orientechnologies.orient.core.metadata.schema.OSchema
import com.orientechnologies.orient.core.metadata.schema.OType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

/**
 * Tests {@link ConfigDatabaseUpgrade_1_2} with an in memory database to ensure adding case insensitive collation to the
 * name property doesn't cause errors.
 */
class ConfigDatabaseUpgrade_1_2__InMemoryTest
    extends TestSupport
{

  static final String DB_CLASS = new OClassNameBuilder()
      .prefix("repository")
      .type("blobstore")
      .build()

  private static final String P_NAME = "name"

  private static final String P_TYPE = "type"

  private static final String P_ATTRIBUTES = "attributes"

  @VisibleForTesting
  static final String I_NAME = new OIndexNameBuilder()
      .type(DB_CLASS)
      .property(P_NAME)
      .build()

  private static final String QUARTZ_JOB_DETAIL_CLASS = new OClassNameBuilder()
      .prefix("quartz")
      .type("job_detail")
      .build()

  @Mock
  PasswordHelper passwordHelper

  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test")

  ConfigDatabaseUpgrade_1_2 underTest

  @Before
  public void setup() {
    //setup the schema as it was prior to the migration
    database.instance.connect().withCloseable { db ->
      OSchema schema = db.getMetadata().getSchema()
      def type = schema.createClass(DB_CLASS)

      type.createProperty(P_NAME, OType.STRING)
          .setMandatory(true)
          .setNotNull(true)
      type.createProperty(P_TYPE, OType.STRING)
          .setMandatory(true)
          .setNotNull(true)
      type.createProperty(P_ATTRIBUTES, OType.EMBEDDEDMAP)
          .setMandatory(true)
          .setNotNull(true)
      type.createIndex(I_NAME, INDEX_TYPE.UNIQUE, P_NAME)

      schema.createClass(QUARTZ_JOB_DETAIL_CLASS)
      schema.createClass("repository")
    }
    underTest = new ConfigDatabaseUpgrade_1_2(database.getInstanceProvider(), database.getInstanceProvider())
  }

  @Test
  public void 'upgrade step can safely be re-run on already upgraded database'() {
    underTest.apply()

    //db shouldn't care if we make the same change twice
    underTest.apply()
  }
}
