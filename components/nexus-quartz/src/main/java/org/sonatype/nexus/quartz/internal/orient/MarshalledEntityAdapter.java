package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.thread.TcclBlock;
import org.sonatype.nexus.orient.entity.IterableEntityAdapter;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * {@link MarshalledEntityAdapter} adapter.
 *
 * @see MarshalledEntity
 * @see Marshaller
 * @since 3.0
 */
public abstract class MarshalledEntityAdapter<T extends MarshalledEntity>
    extends IterableEntityAdapter<T>
{
  private static final String P_VALUE_TYPE = "value_type";

  private static final String P_VALUE_DATA = "value_data";

  private final Marshaller marshaller;

  private final ClassLoader classLoader;

  public MarshalledEntityAdapter(final String typeName, final Marshaller marshaller, final ClassLoader classLoader) {
    super(typeName);
    this.marshaller = checkNotNull(marshaller);
    this.classLoader = checkNotNull(classLoader);
  }

  @Override
  protected void defineType(final OClass type) {
    type.createProperty(P_VALUE_TYPE, OType.STRING)
        .setMandatory(true)
        .setNotNull(true);
    type.createProperty(P_VALUE_DATA, marshaller.getType())
        .setMandatory(true)
        .setNotNull(true);
  }

  @Override
  protected void readFields(final ODocument document, final MarshalledEntity entity) throws Exception {
    String valueType = document.field(P_VALUE_TYPE);
    checkState(valueType != null, "Marshalled document missing value_type: %s", document);
    Class<?> valueClass = classLoader.loadClass(valueType);

    // read value data
    Object valueData = document.field(P_VALUE_DATA);
    checkState(valueData != null, "Marshalled document missing value_data: %s", document);

    // unmarshall
    try (TcclBlock tccl = TcclBlock.begin(classLoader)) {
      Object value = marshaller.unmarshall(valueData, valueClass);

      //noinspection unchecked
      entity.setValue(value);
    }
  }

  @Override
  protected void writeFields(final ODocument document, final MarshalledEntity entity) throws Exception {
    Object value = entity.getValue();
    checkState(value != null, "Marshalled entity missing value: %s", entity);
    String valueType = value.getClass().getName();
    document.field(P_VALUE_TYPE, valueType);

    // marshall
    try (TcclBlock tccl = TcclBlock.begin(classLoader)) {
      Object valueData = marshaller.marshall(value);
      document.field(P_VALUE_DATA, valueData);
    }
  }
}
