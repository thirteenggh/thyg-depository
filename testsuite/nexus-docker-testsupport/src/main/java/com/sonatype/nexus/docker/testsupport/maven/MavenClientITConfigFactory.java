package com.sonatype.nexus.docker.testsupport.maven;

import com.sonatype.nexus.docker.testsupport.framework.DockerCommandLineConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder;

import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultDockerClientBuilder;
import static com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig.Builder.defaultHostConfigBuilder;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @since 3.16
 */
public class MavenClientITConfigFactory
{
  private MavenClientITConfigFactory() {
  }

  public static DockerContainerConfig createMavenConfig(final String image, 
                                                        final String tag,
                                                        final DockerCommandLineConfig config) 
  {
    return configBuilder(image + ":" + tag, config).build();
  }

  private static Builder configBuilder(final String image, final DockerCommandLineConfig config) {
    return DockerContainerConfig.builder()
        .image(image)
        .withHostConfigBuilder(defaultHostConfigBuilder().appendBinds(config.getPathBinds()))
        .withDockerClientBuilder(defaultDockerClientBuilder().readTimeoutMillis(SECONDS.toMillis(5000)));
  }
}
