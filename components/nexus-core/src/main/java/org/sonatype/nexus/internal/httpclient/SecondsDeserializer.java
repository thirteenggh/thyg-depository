package org.sonatype.nexus.internal.httpclient;

import java.io.IOException;

import org.sonatype.goodies.common.Time;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * {@link Time} in seconds deserializer.
 *
 * @since 3.0
 */
public class SecondsDeserializer
    extends StdDeserializer<Time>
{
  public SecondsDeserializer() {
    super(Time.class);
  }

  @Override
  public Time deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
    return Time.seconds(parser.readValueAs(Long.class));
  }
}
