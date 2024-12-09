package org.sonatype.nexus.supportzip.datastore;

import java.io.IOException;

import org.sonatype.goodies.common.Time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * {@link Time} in seconds serializer.
 *
 * @since 3.29
 */
public class SecondsSerializer
    extends StdSerializer<Time>
{
  private static final long serialVersionUID = -4361645021475489026L;

  public SecondsSerializer() {
    super(Time.class);
  }

  @Override
  public void serialize(final Time value, final JsonGenerator jgen, final SerializerProvider provider)
      throws IOException
  {
    jgen.writeNumber(value.toSecondsI());
  }
}
