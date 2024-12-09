package org.sonatype.nexus.repository.maven.rest;

import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.HostedRepositoryApiRequestToConfigurationConverter;

/**
 * @since 3.20
 */
@Named
public class MavenHostedRepositoryApiRequestToConfigurationConverter
    extends HostedRepositoryApiRequestToConfigurationConverter<MavenHostedRepositoryApiRequest>
{
  @Override
  public Configuration convert(final MavenHostedRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("maven").set("versionPolicy", request.getMaven().getVersionPolicy());
    configuration.attributes("maven").set("layoutPolicy", request.getMaven().getLayoutPolicy());
    return configuration;
  }
}
