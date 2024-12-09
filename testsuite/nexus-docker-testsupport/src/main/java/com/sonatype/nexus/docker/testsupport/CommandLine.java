package com.sonatype.nexus.docker.testsupport;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import com.spotify.docker.client.messages.PortBinding;

/**
 * Interface for Command Line IT Support classes to conform to.
 *
 * @since 3.6.1
 */
public interface CommandLine
{
  /**
   * Execute commands on command line.
   *
   * @param commands to execute
   * @return Optional of a {@link List} of {@link String}s representing command line output,
   * present if commands resulted in output.
   */
  Optional<List<String>> exec(String commands);

  /**
   * Download a file locally from command line.
   *
   * @param fromContainerPath the file to download
   * @param toLocal           {@link File} to download to
   * @return Optional of {@link Set} of {@link File}s.
   */
  Optional<Set<File>> download(String fromContainerPath, File toLocal);

  /**
   * Retrieve the {@link PortBinding} that are represented in the container for this host, to request this is only
   * valuable after a containers has been started, as ports are inspected at runtime for random ones assigned.
   *
   * @return Optional of {@link Map} of {@link PortBinding}s.
   */
  Optional<Map<String, List<PortBinding>>> hostPortBindings();

  /**
   * Initialization method that should be called right after creation of a Command Line Client
   * but before actual commands will allowed to execute, this will allow implementers to do any
   * pre-conditional work.
   */
  void init();

  /**
   * Called to exit the command line. Similar as exiting a terminal
   */
  void exit();

  /**
   * Retrieves the TCP Port of the a host, using {@link #hostPortBindings()} to search through the available host ports.
   *
   * @param containerPort the container that is expected to be associated with the host port
   * @return String representing port, or null if none was found
   */
  @Nullable
  String getHostTcpPort(String containerPort);
}
