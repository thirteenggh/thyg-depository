package org.sonatype.nexus.repository.upgrade;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class ConfigDatabaseUpgrade_1_7_Test
    extends TestSupport
{
  static final String DB_CLASS = new OClassNameBuilder()
      .type("repository")
      .build();

  private static final String P_REPOSITORY_NAME = "repositoryName";

  private static final String P_ROUTING_RULE_ID = "routingRuleId";

  @Rule
  public DatabaseInstanceRule configDatabase = DatabaseInstanceRule.inMemory("test_config");

  private ConfigDatabaseUpgrade_1_7 underTest;

  @Before
  public void setUp() {
    underTest = new ConfigDatabaseUpgrade_1_7(configDatabase.getInstanceProvider());
    try (ODatabaseDocumentTx db = configDatabase.getInstance().connect()) {
      OSchema schema = db.getMetadata().getSchema();
      OClass dbClass = schema.createClass(DB_CLASS);
      dbClass.createProperty(P_REPOSITORY_NAME, OType.STRING);

      ODocument document = db.newInstance(DB_CLASS);
      document.save();
    }
  }

  @Test
  public void upgradeAddsRoutingRuleLinkField() throws Exception {
    try (ODatabaseDocumentTx db = configDatabase.getInstance().connect()) {
      OClass table = db.getMetadata().getSchema().getClass(DB_CLASS);
      assertThat(table.getProperty(P_ROUTING_RULE_ID), is(nullValue()));
    }

    underTest.apply();

    try (ODatabaseDocumentTx db = configDatabase.getInstance().connect()) {
      OClass table = db.getMetadata().getSchema().getClass(DB_CLASS);
      assertThat(table.getProperty(P_ROUTING_RULE_ID), is(notNullValue()));

      db.browseClass(DB_CLASS).forEach(record -> {
        EntityId routingRuleId = record.field(P_ROUTING_RULE_ID, OType.LINK);
        assertThat(routingRuleId, is(nullValue()));
      });
    }
  }

  @SuppressWarnings("java:S2699") // sonar wants assertions, but seems best to let an exception bubble up
  @Test
  public void testWithoutExistingDb() throws Exception {
    underTest.apply();
  }
}
