package com.sonatype.nexus.docker.testsupport.npm;

import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder;

/**
 * Factory for creation of NPM required objects
 *
 * @since 3.6.1
 */
public class NpmFactory
{
  private static final String IMAGE_NODE = "docker-all.repo.sonatype.com/node";

  private NpmFactory() {
  }

  public static DockerContainerConfig createNodeConfig(NpmCommandLineConfig config) {
    return npmConfigBuilder(IMAGE_NODE, config).build();
  }

  private static DockerContainerConfig.Builder npmConfigBuilder(String image, NpmCommandLineConfig config) {
    return DockerContainerConfig.builder()
        .image(image)
        .withHostConfigBuilder(Builder.defaultHostConfigBuilder()
            .appendBinds(config.getPathBinds()));
  }
}
