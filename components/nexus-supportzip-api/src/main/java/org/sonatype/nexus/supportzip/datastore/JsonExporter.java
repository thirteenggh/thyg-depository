package org.sonatype.nexus.supportzip.datastore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.goodies.common.Time;
import org.sonatype.nexus.common.io.SanitizingJsonOutputStream;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.ByteStreams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import static org.sonatype.nexus.supportzip.PasswordSanitizing.REPLACEMENT;
import static org.sonatype.nexus.supportzip.PasswordSanitizing.SENSITIVE_FIELD_NAMES;

/**
 * Export/Import data to/from the JSON by replacing sensitive data.
 *
 * @since 3.29
 */
@Named
@Singleton
public class JsonExporter
    extends ComponentSupport
{
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.registerModule(new SimpleModule()
        .addSerializer(Time.class, new SecondsSerializer())
        .addDeserializer(Time.class, new SecondsDeserializer()));
    OBJECT_MAPPER.registerModule(new JavaTimeModule());
    OBJECT_MAPPER.registerModule(new JodaModule());
    OBJECT_MAPPER.registerModule(new Jdk8Module());
    OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  /**
   * Export data to the JSON file and hide sensitive fields.
   *
   * @param objects to be exported.
   * @param file    where to export.
   * @throws IOException for any issue during writing a file.
   */
  public <T> void exportToJson(final List<T> objects, final File file) throws IOException {
    if (objects != null && !objects.isEmpty()) {
      try (ByteArrayInputStream is = new ByteArrayInputStream(OBJECT_MAPPER.writeValueAsBytes(objects));
           OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
           SanitizingJsonOutputStream stream = new SanitizingJsonOutputStream(os, SENSITIVE_FIELD_NAMES, REPLACEMENT)) {
        ByteStreams.copy(is, stream);
      }
    }
  }

  /**
   * Export data to the JSON file and hide sensitive fields.
   *
   * @param object to be exported.
   * @param file   where to export.
   * @throws IOException for any issue during writing a file.
   */
  public <T> void exportObjectToJson(final T object, final File file) throws IOException {
    if (object != null) {
      try (ByteArrayInputStream is = new ByteArrayInputStream(OBJECT_MAPPER.writeValueAsBytes(object));
           OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
           SanitizingJsonOutputStream stream = new SanitizingJsonOutputStream(os, SENSITIVE_FIELD_NAMES, REPLACEMENT)) {
        ByteStreams.copy(is, stream);
      }
    }
  }

  /**
   * Read JSON data.
   *
   * @param file  file where data will be read.
   * @param clazz the type of imported data.
   * @return the list of {@link T} objects.
   * @throws IOException for any issue during reading a file.
   */
  public <T> List<T> importFromJson(final File file, final Class<T> clazz) throws IOException {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      String jsonData = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
      if (StringUtils.isNotBlank(jsonData)) {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return OBJECT_MAPPER.readValue(jsonData, type);
      }
    }

    return Collections.emptyList();
  }

  /**
   * Read JSON data.
   *
   * @param file  file where data will be read.
   * @param clazz the type of imported data.
   * @return {@link T} object or {@link Optional#empty} is case of an empty JSON file.
   * @throws IOException for any issue during reading a file.
   */
  public <T> Optional<T> importObjectFromJson(final File file, final Class<T> clazz) throws IOException {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      String jsonData = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
      if (StringUtils.isNotBlank(jsonData)) {
        return Optional.of(OBJECT_MAPPER.readValue(jsonData, clazz));
      }
    }
    return Optional.empty();
  }
}
