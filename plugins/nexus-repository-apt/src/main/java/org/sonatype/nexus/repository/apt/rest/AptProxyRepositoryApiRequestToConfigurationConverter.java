package org.sonatype.nexus.repository.apt.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.ProxyRepositoryApiRequestToConfigurationConverter;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

/**
 * @since 3.20
 */
@Named
public class AptProxyRepositoryApiRequestToConfigurationConverter
    extends ProxyRepositoryApiRequestToConfigurationConverter<AptProxyRepositoryApiRequest>
{
  @Inject
  public AptProxyRepositoryApiRequestToConfigurationConverter(final RoutingRuleStore routingRuleStore) {
    super(routingRuleStore);
  }

  @Override
  public Configuration convert(final AptProxyRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("apt").set("distribution", request.getApt().getDistribution());
    configuration.attributes("apt").set("flat", request.getApt().getFlat());
    return configuration;
  }
}
