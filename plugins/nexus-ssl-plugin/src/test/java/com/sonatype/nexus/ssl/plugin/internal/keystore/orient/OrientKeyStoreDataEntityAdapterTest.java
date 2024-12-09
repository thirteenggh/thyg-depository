package com.sonatype.nexus.ssl.plugin.internal.keystore.orient;

import java.nio.charset.StandardCharsets;

import com.sonatype.nexus.ssl.plugin.internal.keystore.KeyStoreDataEvent;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.EntityEvent;
import org.sonatype.nexus.orient.entity.EntityAdapter.EventKind;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class OrientKeyStoreDataEntityAdapterTest
    extends TestSupport
{
  private static final String KEY_STORE_NAME = "ssl/test.ks";

  private static final byte[] KEY_STORE_DATA = "test-key-store-data".getBytes(StandardCharsets.UTF_8);

  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test");

  private OrientKeyStoreDataEntityAdapter entityAdapter;

  @Before
  public void setUp() {
    entityAdapter = new OrientKeyStoreDataEntityAdapter();
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

      assertThat(entityAdapter.load(db, KEY_STORE_NAME), is(nullValue()));

      OrientKeyStoreData entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(KEY_STORE_DATA);
      entityAdapter.save(db, entity);

      entity = entityAdapter.load(db, KEY_STORE_NAME);
      assertThat(entity, is(notNullValue()));
      assertThat(entity.getName(), is(KEY_STORE_NAME));
      assertThat(entity.getBytes(), is(KEY_STORE_DATA));

      entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(new byte[0]);
      entityAdapter.save(db, entity);

      entity = entityAdapter.load(db, KEY_STORE_NAME);
      assertThat(entity, is(notNullValue()));
      assertThat(entity.getName(), is(KEY_STORE_NAME));
      assertThat(entity.getBytes(), is(new byte[0]));
    }
  }

  @Test(expected = ORecordDuplicatedException.class)
  public void testIndex_EnforceUniqueName() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);

      OrientKeyStoreData entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(KEY_STORE_DATA);
      entityAdapter.save(db, entity);

      entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(KEY_STORE_DATA);
      entityAdapter.addEntity(db, entity);
    }
  }

  @Test
  public void testNewEvent_Create() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);

      OrientKeyStoreData entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(KEY_STORE_DATA);
      ODocument document = entityAdapter.addEntity(db, entity);

      EntityEvent event = entityAdapter.newEvent(document, EventKind.CREATE);
      assertThat(event, is(instanceOf(OrientKeyStoreDataCreatedEvent.class)));
      assertThat(((KeyStoreDataEvent) event).getKeyStoreName(), is(KEY_STORE_NAME));
    }
  }

  @Test
  public void testNewEvent_Update() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);

      OrientKeyStoreData entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(KEY_STORE_DATA);
      ODocument document = entityAdapter.addEntity(db, entity);

      EntityEvent event = entityAdapter.newEvent(document, EventKind.UPDATE);
      assertThat(event, is(instanceOf(OrientKeyStoreDataUpdatedEvent.class)));
      assertThat(((KeyStoreDataEvent) event).getKeyStoreName(), is(KEY_STORE_NAME));
    }
  }

  @Test
  public void testNewEvent_Delete() {
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      entityAdapter.register(db);

      OrientKeyStoreData entity = new OrientKeyStoreData();
      entity.setName(KEY_STORE_NAME);
      entity.setBytes(KEY_STORE_DATA);
      ODocument document = entityAdapter.addEntity(db, entity);

      EntityEvent event = entityAdapter.newEvent(document, EventKind.DELETE);
      assertThat(event, is(nullValue()));
    }
  }
}
