package org.sonatype.nexus.repository.json;

import com.fasterxml.jackson.databind.deser.std.MapDeserializer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class that acts as a {@link MapDeserializer} but allows the results to be serialized out right away by
 * using a {@link UntypedObjectDeserializerSerializer} for its deserialization of individual objects and arrays.
 */
public class MapDeserializerSerializer
    extends MapDeserializer
{
  public MapDeserializerSerializer(final MapDeserializer rootDeserializer,
                                   final UntypedObjectDeserializerSerializer deserializerSerializer)
  {
    super(checkNotNull(rootDeserializer).getValueType(), checkNotNull(rootDeserializer).getValueInstantiator(), null,
        checkNotNull(deserializerSerializer), null);
  }
}
