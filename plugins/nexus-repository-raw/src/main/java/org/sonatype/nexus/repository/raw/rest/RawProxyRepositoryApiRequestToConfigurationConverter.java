package org.sonatype.nexus.repository.raw.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.ProxyRepositoryApiRequestToConfigurationConverter;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

import static org.sonatype.nexus.repository.raw.rest.RawAttributes.CONTENT_DISPOSITION;

/**
 * @since 3.25
 */
@Named
public class RawProxyRepositoryApiRequestToConfigurationConverter
    extends ProxyRepositoryApiRequestToConfigurationConverter<RawProxyRepositoryApiRequest>
{
  @Inject
  public RawProxyRepositoryApiRequestToConfigurationConverter(final RoutingRuleStore routingRuleStore) {
    super(routingRuleStore);
  }

  @Override
  public Configuration convert(final RawProxyRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("raw").set(CONTENT_DISPOSITION, request.getRaw().getContentDisposition().name());
    return configuration;
  }
}
