package org.sonatype.nexus.repository.search;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * JSON related utilities.
 *
 * @since 3.0
 */
class JsonUtils
{
  private JsonUtils() {
    // empty
  }

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();

  /**
   * Converts any object to JSON.
   */
  public static String from(final Object value) throws IOException {
    return WRITER.writeValueAsString(value);
  }
}
