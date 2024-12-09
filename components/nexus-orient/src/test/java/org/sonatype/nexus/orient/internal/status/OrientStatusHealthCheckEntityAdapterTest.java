package org.sonatype.nexus.orient.internal.status;

import java.util.Date;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.orient.internal.status.OrientStatusHealthCheckEntityAdapter.NodeHealthCheck;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class OrientStatusHealthCheckEntityAdapterTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test");

  private OrientStatusHealthCheckEntityAdapter entityAdapter;

  @Before
  public void before() {
    entityAdapter = new OrientStatusHealthCheckEntityAdapter();
  }

  @Test
  public void testRegister() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);
      OSchema schema = db.getMetadata().getSchema();
      assertThat(schema.getClass(entityAdapter.getTypeName()), is(notNullValue()));
    }
  }

  @Test
  public void testReadWrite() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);

      NodeHealthCheck entity = entityAdapter.read(db, "node1");
      assertThat(entity, is(nullValue()));

      Date expectedTime = new Date();
      entity = entityAdapter.newEntity();
      entity.nodeId = "node1";
      entity.lastHealthCheck = expectedTime;
      entityAdapter.addEntity(db, entity);

      entity = entityAdapter.read(db, "node1");
      assertThat(entity, is(notNullValue()));
      assertThat(entity.lastHealthCheck, is(expectedTime));
    }
  }
}
