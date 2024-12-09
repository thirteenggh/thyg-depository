package com.sonatype.nexus.docker.testsupport.conda;

import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder;

import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultDockerClientBuilder;
import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultHostConfigBuilder;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Factory for creation of Conda required objects
 *
 * @since 3.19
 */
public class CondaClientITConfigFactory
{
  private static final String IMAGE_CONDA = "docker-all.repo.sonatype.com/continuumio/miniconda3";

  private CondaClientITConfigFactory() {
  }

  public static DockerContainerConfig createCondaConfig() {
    return condaConfigBuilder(IMAGE_CONDA).build();
  }

  private static Builder condaConfigBuilder(final String image) {
    return DockerContainerConfig.builder()
        .image(image)
        .withHostConfigBuilder(defaultHostConfigBuilder())
        .withDockerClientBuilder(defaultDockerClientBuilder().readTimeoutMillis(SECONDS.toMillis(5000)));
  }
}
