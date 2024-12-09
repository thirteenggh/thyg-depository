package org.sonatype.nexus.repository.storage;

import java.util.ArrayList;
import java.util.List;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.id.ORID;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetadataNodeEntityAdapterTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("test");

  List<Bucket> buckets;

  @Mock
  BucketEntityAdapter bucketEntityAdapter;

  MetadataNodeEntityAdapter<?> underTest;

  @Before
  public void setup() throws Exception {
    buckets = new ArrayList<>();
    buckets.add(makeBucket("orid"));
    underTest = new TestableMetadataNodeEntityAdapter("type", bucketEntityAdapter);
  }

  @Test
  public void addBucketConstraints() throws Exception {
    StringBuilder query = new StringBuilder();
    underTest.addBucketConstraints("where clause", buckets, query);
    assertThat(query.toString(), is(equalTo(" and (bucket=orid)")));
  }

  @Test
  public void addBucketConstraintsWithWhere() throws Exception {
    StringBuilder query = new StringBuilder();
    underTest.addBucketConstraints(null, buckets, query);
    assertThat(query.toString(), is(equalTo(" where (bucket=orid)")));
  }

  @Test
  public void addBucketConstraintsWithWhereForMultipleBuckets() throws Exception {
    buckets.add(makeBucket("orid2"));
    StringBuilder query = new StringBuilder();
    underTest.addBucketConstraints(null, buckets, query);
    assertThat(query.toString(), is(equalTo(" where (bucket=orid or bucket=orid2)")));
  }

  @Test
  public void emptyBucketsBrowseByQuery() throws Exception {
    buckets.clear();
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      assertThat(underTest.browseByQuery(db, null, null, buckets, null), is(emptyIterable()));
    }
  }

  @Test
  public void emptyBucketsCountByQuery() throws Exception {
    buckets.clear();
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      assertThat(underTest.countByQuery(db, null, null, buckets, null), is(0L));
    }
  }


  @Test
  public void emptyBucketsCountGroupByQuery() throws Exception {
    buckets.clear();
    try (ODatabaseDocumentTx db = database.getInstance().connect()) {
      assertThat(underTest.countGroupByQuery(db, null, null, buckets, "group by name"), is(0L));
    }
  }

  private Bucket makeBucket(final String id) {
    Bucket bucket = mock(Bucket.class);
    ORID orid = mock(ORID.class);
    when(bucketEntityAdapter.recordIdentity(bucket)).thenReturn(orid);
    when(orid.toString()).thenReturn(id);
    return bucket;
  }

  private static class TestableMetadataNodeEntityAdapter
      extends MetadataNodeEntityAdapter<Asset>
  {
    public TestableMetadataNodeEntityAdapter(final String typeName,
                                             final BucketEntityAdapter bucketEntityAdapter)
    {
      super(typeName, bucketEntityAdapter);
    }

    @Override
    protected Asset newEntity() {
      return null;
    }
  }
}
