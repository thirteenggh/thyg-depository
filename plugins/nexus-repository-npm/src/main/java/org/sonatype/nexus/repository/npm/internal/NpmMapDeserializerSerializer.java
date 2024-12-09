package org.sonatype.nexus.repository.npm.internal;

import java.util.List;

import org.sonatype.nexus.repository.json.MapDeserializerSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;

/**
 * NPM Specialized {@link MapDeserializerSerializer} that uses a {@link NpmUntypedObjectDeserializerSerializer} for
 * it object deserialization and serializing out.
 *
 * @since 3.16
 */
public class NpmMapDeserializerSerializer
    extends MapDeserializerSerializer
{
  public NpmMapDeserializerSerializer(final MapDeserializer rootDeserializer,
                                      final JsonGenerator generator,
                                      final List<NpmFieldMatcher> matchers)
  {
    super(rootDeserializer, new NpmUntypedObjectDeserializerSerializer(generator, matchers));
  }
}
