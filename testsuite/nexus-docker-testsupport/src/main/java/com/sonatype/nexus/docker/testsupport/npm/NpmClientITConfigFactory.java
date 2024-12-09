package com.sonatype.nexus.docker.testsupport.npm;

import com.sonatype.nexus.docker.testsupport.framework.DockerCommandLineConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder;

import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultDockerClientBuilder;
import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultHostConfigBuilder;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @since 3.16
 */
public class NpmClientITConfigFactory
{
  private static final String IMAGE = "docker-all.repo.sonatype.com/node";

  private NpmClientITConfigFactory() {
  }

  public static DockerContainerConfig createConfig(final String dockerTag, final DockerCommandLineConfig config) {
    return configBuilder(IMAGE + ":" + dockerTag, config).build();
  }

  private static Builder configBuilder(final String image, final DockerCommandLineConfig config) {
    return DockerContainerConfig.builder()
        .image(image)
        .withHostConfigBuilder(defaultHostConfigBuilder().appendBinds(config.getPathBinds()))
        .withDockerClientBuilder(defaultDockerClientBuilder().readTimeoutMillis(SECONDS.toMillis(5000)));
  }
}
