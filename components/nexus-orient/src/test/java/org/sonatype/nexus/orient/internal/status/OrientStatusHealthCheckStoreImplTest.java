package org.sonatype.nexus.orient.internal.status;

import org.sonatype.nexus.common.app.NotReadableException;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.google.common.collect.Iterators;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class OrientStatusHealthCheckStoreImplTest
{
  @Rule
  public DatabaseInstanceRule db = DatabaseInstanceRule.inMemory("test");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private NodeAccess nodeAccess;

  private OrientStatusHealthCheckEntityAdapter entityAdapter;

  private OrientStatusHealthCheckStore store;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    when(nodeAccess.getId()).thenReturn("node1");

    entityAdapter = new OrientStatusHealthCheckEntityAdapter();
    store = new OrientStatusHealthCheckStore(db.getInstanceProvider(), entityAdapter, nodeAccess);
    store.doStart();
  }

  @Test
  public void testDoStartRegistersEntity() {
    try (ODatabaseDocumentTx instance = db.getInstance().connect()) {
      OSchemaProxy schema = instance.getMetadata().getSchema();
      assertThat("database did not contain entity type after start", schema.getClass(entityAdapter.getTypeName()),
          is(notNullValue()));
    }
  }

  @Test
  public void testNoInitialValues() {
    try (ODatabaseDocumentTx instance = db.getInstance().connect()) {
      assertThat("should have no health checks initially", instance.countClass(entityAdapter.getTypeName()) == 0);
    }
  }

  @Test
  public void testHealthCheckMark() throws Exception {
    store.checkWritable("");

    try (ODatabaseDocumentTx instance = db.getInstance().connect()) {
      ORecordIteratorClass<ODocument> documents = instance.browseClass(entityAdapter.getTypeName());
      assertThat("failed to write health check time", documents.hasNext(), is(true));
      assertThat(Iterators.size(documents), is(1));
    }

    // repeat the mark and ensure no new records were added
    store.checkWritable("");
    try (ODatabaseDocumentTx instance = db.getInstance().connect()) {
      ORecordIteratorClass<ODocument> documents = instance.browseClass(entityAdapter.getTypeName());
      assertThat(Iterators.size(documents), is(1));
    }
  }

  @Test
  public void testCheckReadHealth() throws Exception {
    // no record exists yet. this is ok
    store.checkReadable("");

    // add record to the database
    store.checkWritable("");
    store.checkReadable("");

    // stop database
    db.getServer().stopAbnormally();
    thrown.expect(NotReadableException.class);
    store.checkReadable("");
  }
}
