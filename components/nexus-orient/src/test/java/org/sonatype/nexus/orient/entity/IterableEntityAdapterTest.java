package org.sonatype.nexus.orient.entity;

import java.util.Arrays;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.common.entity.Entity;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class IterableEntityAdapterTest
    extends TestSupport
{
  @Spy
  IterableEntityAdapter<Entity> underTest = new TestIterableEntityAdapter();

  @Mock
  Entity goodEntity;

  @Mock
  ODocument goodRecord;

  @Mock
  ODocument badRecord;

  @Before
  public void setUp() {
    when(goodRecord.getRecord()).thenReturn(goodRecord);
    when(badRecord.getRecord()).thenReturn(badRecord);
    when(underTest.readEntity(goodRecord)).thenReturn(goodEntity);
    when(underTest.readEntity(badRecord)).thenThrow(new NoClassDefFoundError());
  }

  @Test
  public void nullEntitiesAreSkipped() {
    assertThat(underTest.transform(Arrays.asList(null, goodRecord, null)), contains(goodEntity));
  }

  @Test
  public void malformedEntitiesAreSkipped() {
    assertThat(underTest.transform(Arrays.asList(badRecord, goodRecord)), contains(goodEntity));
  }

  private class TestIterableEntityAdapter
      extends IterableEntityAdapter<Entity>
  {
    public TestIterableEntityAdapter() {
      super("test");
    }

    @Override
    protected void defineType(final OClass type) {
      // no-op
    }

    @Override
    protected Entity newEntity() {
      return new AbstractEntity()
      {
        // empty
      };
    }

    @Override
    protected void attachMetadata(final Entity entity, final ODocument document) {
      // no-op
    }

    @Override
    protected void readFields(final ODocument document, final Entity entity) throws Exception {
      // no-op
    }

    @Override
    protected void writeFields(final ODocument document, final Entity entity) throws Exception {
      // no-op
    }
  }
}
