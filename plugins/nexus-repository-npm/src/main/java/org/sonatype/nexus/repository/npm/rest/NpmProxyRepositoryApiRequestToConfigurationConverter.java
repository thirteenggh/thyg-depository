package org.sonatype.nexus.repository.npm.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.ProxyRepositoryApiRequestToConfigurationConverter;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

import static org.sonatype.nexus.repository.npm.rest.NpmAttributes.REMOVE_NON_CATALOGED;

/**
 * @since 3.29
 */
@Named
public class NpmProxyRepositoryApiRequestToConfigurationConverter
    extends ProxyRepositoryApiRequestToConfigurationConverter<NpmProxyRepositoryApiRequest>
{
  @Inject
  public NpmProxyRepositoryApiRequestToConfigurationConverter(final RoutingRuleStore routingRuleStore) {
    super(routingRuleStore);
  }

  @Override
  public Configuration convert(final NpmProxyRepositoryApiRequest request) {
    Configuration convert = super.convert(request);
    NpmAttributes npm = request.getNpm();
    if (npm != null) {
      convert.attributes("npm").set(REMOVE_NON_CATALOGED, npm.getRemoveNonCataloged());
    }
    return convert;
  }
}
