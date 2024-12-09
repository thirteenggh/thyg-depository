package com.sonatype.nexus.docker.testsupport.nginx;

import com.sonatype.nexus.docker.testsupport.ContainerCommandLineITSupport;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;

/**
 * Nginx implementation of a Docker Command Line enabled container.
 *
 * @since 3.16
 */
public class NginxCommandLineITSupport
    extends ContainerCommandLineITSupport
{
  private static final String CMD_SERVICE = "service ";

  private static final String CMD_NGINX = "nginx ";

  /**
   * Constructor.
   *
   * @param dockerContainerConfig {@link DockerContainerConfig}
   */
  public NginxCommandLineITSupport(DockerContainerConfig dockerContainerConfig) {
    super(dockerContainerConfig);
  }

  /**
   * Runs a nginx server by running <code>service nginx start</code>
   */
  public void nginxServiceStart() {
    exec(CMD_SERVICE + CMD_NGINX + "start > /dev/null 2>&1");
  }

  /**
   * Stops a nginx server by running <code>service nginx stop</code>
   */
  public void nginxServiceStop() {
    exec(CMD_SERVICE + CMD_NGINX + "stop > /dev/null 2>&1");
  }
}
