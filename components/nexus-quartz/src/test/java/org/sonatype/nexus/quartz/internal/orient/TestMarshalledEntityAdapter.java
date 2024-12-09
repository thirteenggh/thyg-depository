package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.orient.OClassNameBuilder;

/**
 * {@link TestMarshalledEntity} entity-adapter.
 */
public class TestMarshalledEntityAdapter
    extends MarshalledEntityAdapter<TestMarshalledEntity>
{
  private static final String DB_CLASS = new OClassNameBuilder()
      .prefix("test")
      .type("marshalled_entity")
      .build();

  public TestMarshalledEntityAdapter() {
    super(DB_CLASS, new JacksonMarshaller(new FieldObjectMapper()), TestMarshalledValue.class.getClassLoader());
  }

  @Override
  protected TestMarshalledEntity newEntity() {
    return new TestMarshalledEntity();
  }
}
