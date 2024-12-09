package com.sonatype.nexus.docker.testsupport.framework;

import java.util.List;

import com.spotify.docker.client.messages.HostConfig;

import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultDockerClientBuilder;
import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.PORT_MAPPING_IP;
import static com.spotify.docker.client.messages.PortBinding.randomPort;
import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toMap;

/**
 * Factory for creation of Docker required objects
 *
 * @since 3.16
 */
public class DockerClientITConfigFactory
{
  private static final String IMAGE_NGINX = "docker-all.repo.sonatype.com/nginx";

  private DockerClientITConfigFactory() {
  }

  public static DockerContainerConfig createNginxConfig(final DockerCommandLineConfig config,
                                                        final List<String> portMappingPorts)
  {
    return nginxConfigBuilder(IMAGE_NGINX, portMappingPorts, config).build();
  }

  private static DockerContainerConfig.Builder nginxConfigBuilder(final String image,
                                                                  final List<String> portMappingPorts,
                                                                  final DockerCommandLineConfig config)
  {
    HostConfig.Builder hostConfig = HostConfig.builder()
        .portBindings(portMappingPorts.stream()
            .collect(toMap(o -> o, b -> singletonList(randomPort(PORT_MAPPING_IP)))));

    return DockerContainerConfig.builder()
        .image(image)
        .withHostConfigBuilder(hostConfig.appendBinds(config.getPathBinds()))
        .withDockerClientBuilder(defaultDockerClientBuilder().readTimeoutMillis(SECONDS.toMillis(5000)));
  }
}
