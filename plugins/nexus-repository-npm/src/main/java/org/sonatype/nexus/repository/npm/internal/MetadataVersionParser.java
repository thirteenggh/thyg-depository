package org.sonatype.nexus.repository.npm.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Parses metadata to extract values without loading the entire model into memory
 *
 * @since 3.29
 */
public class MetadataVersionParser
{
  public static final String VERSIONS = "versions";

  public static List<String> readVersions(final InputStream is) throws IOException {
    try (JsonReader jsonReader = new JsonReader(new InputStreamReader(is))) {
      return extractVersions(jsonReader);
    }
  }

  private static List<String> extractVersions(final JsonReader reader) throws IOException {
    reader.beginObject();
    List<String> versions = new ArrayList<>();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (VERSIONS.equals(name)) {
        versions = readVersions(reader);
      }
      else {
        reader.skipValue();
      }
    }
    reader.endObject();
    return versions;
  }

  private static List<String> readVersions(final JsonReader reader) throws IOException {
    List<String> versions = new ArrayList<>();
    JsonToken peek = reader.peek();
    if (peek.equals(JsonToken.NULL)) {
      reader.skipValue();
      return versions;
    }
    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      versions.add(name);
      reader.skipValue();
    }
    reader.endObject();
    return versions;
  }
}
