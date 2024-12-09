package org.sonatype.nexus.repository.search.index;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

  /**
   * Merges json objects.
   */
  public static String merge(final String... jsons) throws IOException {
    JsonNode merged = MAPPER.createObjectNode();
    for (String json : jsons) {
      merged = merge(merged, MAPPER.readTree(json));
    }
    return WRITER.writeValueAsString(merged);
  }

  /**
   * Merges json objects.
   */
  public static JsonNode merge(final JsonNode mainNode, final JsonNode updateNode) {
    Iterator<String> fieldNames = updateNode.fieldNames();
    while (fieldNames.hasNext()) {
      String fieldName = fieldNames.next();
      JsonNode jsonNode = mainNode.get(fieldName);
      // if field exists and is an embedded object
      if (jsonNode != null && jsonNode.isObject()) {
        merge(jsonNode, updateNode.get(fieldName));
      }
      else {
        if (mainNode instanceof ObjectNode) {
          // Overwrite field
          JsonNode value = updateNode.get(fieldName);
          ((ObjectNode) mainNode).set(fieldName, value);
        }
      }
    }
    return mainNode;
  }
}
