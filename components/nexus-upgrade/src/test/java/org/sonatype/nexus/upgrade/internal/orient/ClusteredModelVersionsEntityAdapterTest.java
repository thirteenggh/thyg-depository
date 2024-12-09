package org.sonatype.nexus.upgrade.internal.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ClusteredModelVersionsEntityAdapterTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test");

  private ClusteredModelVersionsEntityAdapter entityAdapter;

  @Before
  public void setUp() {
    entityAdapter = new ClusteredModelVersionsEntityAdapter();
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
  public void testSaveAndLoad() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);

      ClusteredModelVersions entity = entityAdapter.get(db);
      assertThat(entity, is(notNullValue()));
      assertThat(entity.getModelVersions().entrySet(), hasSize(0));
      assertThat(entity.isDirty(), is(false));

      entity = new ClusteredModelVersions();
      assertThat(entity.isDirty(), is(false));
      entity.put("model-a", "1.2");
      entity.put("model-b", "2.1");
      assertThat(entity.isDirty(), is(true));
      entityAdapter.set(db, entity);

      entity = entityAdapter.get(db);
      assertThat(entity, is(notNullValue()));
      assertThat(entity.getModelVersions(), hasEntry("model-a", "1.2"));
      assertThat(entity.getModelVersions(), hasEntry("model-b", "2.1"));
      assertThat(entity.getModelVersions().entrySet(), hasSize(2));

      assertThat(entity.isDirty(), is(false));
      entity.put("model-a", "1.3");
      assertThat(entity.isDirty(), is(true));
      entityAdapter.set(db, entity);

      entity = entityAdapter.get(db);
      assertThat(entity, is(notNullValue()));
      assertThat(entity.getModelVersions(), hasEntry("model-a", "1.3"));
      assertThat(entity.getModelVersions(), hasEntry("model-b", "2.1"));
      assertThat(entity.getModelVersions().entrySet(), hasSize(2));
      assertThat(entity.isDirty(), is(false));
    }
  }
}
