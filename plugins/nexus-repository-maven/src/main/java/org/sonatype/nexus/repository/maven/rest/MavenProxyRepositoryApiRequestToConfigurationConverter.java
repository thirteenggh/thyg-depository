package org.sonatype.nexus.repository.maven.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.ProxyRepositoryApiRequestToConfigurationConverter;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

/**
 * @since 3.20
 */
@Named
public class MavenProxyRepositoryApiRequestToConfigurationConverter
    extends ProxyRepositoryApiRequestToConfigurationConverter<MavenProxyRepositoryApiRequest>
{
  @Inject
  public MavenProxyRepositoryApiRequestToConfigurationConverter(final RoutingRuleStore routingRuleStore) {
    super(routingRuleStore);
  }

  @Override
  public Configuration convert(final MavenProxyRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("maven").set("versionPolicy", request.getMaven().getVersionPolicy());
    configuration.attributes("maven").set("layoutPolicy", request.getMaven().getLayoutPolicy());
    return configuration;
  }
}
