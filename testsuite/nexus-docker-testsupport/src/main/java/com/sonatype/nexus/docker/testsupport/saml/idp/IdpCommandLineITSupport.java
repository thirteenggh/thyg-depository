package com.sonatype.nexus.docker.testsupport.saml.idp;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.sonatype.nexus.docker.testsupport.ContainerCommandLineITSupport;
import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;

import org.sonatype.goodies.common.Loggers;

import org.slf4j.Logger;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.sonatype.nexus.common.io.NetworkHelper.findLocalHostAddress;
import static org.sonatype.nexus.pax.exam.NexusPaxExamSupport.waitFor;

public class IdpCommandLineITSupport
    extends ContainerCommandLineITSupport
{
  public static final  String IDP_CONTAINER_PORT = "8080";

  protected final Logger log = Loggers.getLogger(this);

  private String idpHostPort;

  private String idpHost;

  public IdpCommandLineITSupport(final DockerContainerConfig dockerContainerConfig) {
    super(dockerContainerConfig, null);
  }

  public void awaitIdpServer() throws Exception {
    idpHostPort = getHostTcpPort(IDP_CONTAINER_PORT);
    idpHost = findLocalHostAddress();

    log.info("Awaiting idp server {}:{}", idpHost, idpHostPort);

    waitFor(this::isIdpServerAvailable, 120000); // waiting 120 seconds (like IQ)

    log.info("Finished waiting for idp server {}:{}", idpHost, idpHostPort);
  }

  private boolean isIdpServerAvailable() {
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) getIdpUrl().openConnection();
      connection.setConnectTimeout(1000);
      connection.setReadTimeout(1000);
      connection.getInputStream();
      return true;
    }
    catch (Exception ignore) { //NOSONAR
      return false;
    }
    finally {
      if (nonNull(connection)) {
        connection.disconnect();
      }
    }
  }

  public URL getIdpUrl() throws MalformedURLException {
    return getIdpUri().toURL();
  }

  public URI getIdpUri() {
    return URI.create(format("http://%s:%s", idpHost, idpHostPort)).normalize();
  }

  public String getIdpHost() {
    return idpHost;
  }

  public String getIdpHostPort() {
    return idpHostPort;
  }
}
