package com.sonatype.nexus.docker.testsupport.pypi;

import com.sonatype.nexus.docker.testsupport.framework.DockerCommandLineConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder;

import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultDockerClientBuilder;
import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultHostConfigBuilder;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Factory for creation of PyPi required objects
 *
 * @since 3.13
 */
public class PyPiClientITConfigFactory
{
  private static final String IMAGE_PYTHON = "docker-all.repo.sonatype.com/python";

  private PyPiClientITConfigFactory() {
  }

  public static DockerContainerConfig createPythonAlpineConfig(final String tag, final DockerCommandLineConfig config) {
    return configBuilder(IMAGE_PYTHON + ":" + tag, config).build();
  }

  private static Builder configBuilder(final String image, final DockerCommandLineConfig config) {
    return DockerContainerConfig.builder()
        .image(image)
        .withHostConfigBuilder(defaultHostConfigBuilder().appendBinds(config.getPathBinds()))
        .withDockerClientBuilder(defaultDockerClientBuilder().readTimeoutMillis(SECONDS.toMillis(5000)));
  }
}
