package org.sonatype.nexus.orient.internal.freeze;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.orient.freeze.DatabaseFrozenStateManager;
import org.sonatype.nexus.orient.freeze.FreezeRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Default implementation of {@link DatabaseFrozenStateManager}
 *
 * {@link FreezeRequest}s are serialized to JSON in the frozen.marker file.
 *
 * @since 3.3
 */
@Named("local")
@Singleton
public class LocalDatabaseFrozenStateManager
    extends ComponentSupport
    implements DatabaseFrozenStateManager
{
  private static final String FROZEN_MARKER = "frozen.marker";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final File frozenMarkerFile;

  @Inject
  public LocalDatabaseFrozenStateManager(final ApplicationDirectories applicationDirectories) {
    this.frozenMarkerFile = new File(applicationDirectories.getWorkDirectory("db"), FROZEN_MARKER);
    objectMapper.registerModule(new JodaModule());
    // make sure jackson doesn't try to coax the FreezeRequest#timestamp into system time zone.
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
  }

  @Override
  public synchronized List<FreezeRequest> getState() {
    return readMarkerFile();
  }

  @Override
  public synchronized boolean remove(final FreezeRequest request) {
    List<FreezeRequest> requests = readMarkerFile();
    if (requests.remove(request)) {
      writeMarkerFile(requests);
      return true;
    }
    return false;
  }

  @Override
  public synchronized FreezeRequest add(final FreezeRequest request) {
    List<FreezeRequest> requests = readMarkerFile();
    if (requests.add(request)) {
      writeMarkerFile(requests);
      return request;
    }
    return null;
  }

  /**
   * If the marker file doesn't exist, create it and drop an empty array in it.
   *
   * @return the marker file, always
   */
  private File markerFile() {
    if (!Files.exists(frozenMarkerFile.toPath())) {
      try {
        Files.createFile(frozenMarkerFile.toPath());
        this.objectMapper.writeValue(frozenMarkerFile, Collections.emptyList());
      }
      catch (IOException e) {
        throw new IllegalStateException("unable to create database frozen state marker file", e);
      }
    }
    return frozenMarkerFile;
  }

  /**
   * @param requests the list of {@link FreezeRequest}s to write to the marker file
   */
  private void writeMarkerFile(List<FreezeRequest> requests) {
    try {
      this.objectMapper.writeValue(markerFile(), requests);
    }
    catch (IOException e) {
      throw new IllegalStateException("unable to write database frozen state marker file", e);
    }
  }

  /**
   * @return the contents of the marker file, always
   */
  private List<FreezeRequest> readMarkerFile() {
    try {
      return objectMapper.readValue(markerFile(), new TypeReference<List<FreezeRequest>>(){});
    }
    catch (IOException e) {
      throw new IllegalStateException("unable to read database frozen state marker file", e);
    }
  }
}
