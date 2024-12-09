package org.sonatype.repository.helm.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.repository.helm.internal.metadata.ChartIndex;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility methods for getting attributes from yaml files, writing to yaml files
 *
 * @since 3.28
 */
@Named
@Singleton
public class YamlParser
    extends ComponentSupport
{
  private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()
      .disable(Feature.WRITE_DOC_START_MARKER)
      .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
      .configure(Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS, true))
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .setSerializationInclusion(Include.NON_NULL)
      .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE
      = new TypeReference<Map<String, Object>>() { };

  public Map<String, Object> load(InputStream is) throws IOException {
    checkNotNull(is);
    return mapper.readValue(is, MAP_TYPE_REFERENCE);
  }

  public String getYamlContent(final ChartIndex index) {
    try {
      return mapper.writeValueAsString(index);
    }
    catch (IOException ex) {
      log.error("Error in index.yaml", ex);
      return null;
    }
  }

  public void write(final OutputStream os, final ChartIndex index) {
    try (OutputStreamWriter writer = new OutputStreamWriter(os)) {
      String result = getYamlContent(index);
      writer.write(result);
    }
    catch (IOException ex) {
      log.error("Unable to write to OutputStream for index.yaml", ex);
    }
  }
}
