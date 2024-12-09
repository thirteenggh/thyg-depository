package org.sonatype.nexus.repository.rest.internal.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.routing.RoutingRuleHelper;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.repository.rest.internal.resources.RoutingRulesApiResourceBeta.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.BETA_API_PREFIX;

@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class RoutingRulesApiResourceBeta
  extends RoutingRulesApiResource
{
  static final String RESOURCE_URI = BETA_API_PREFIX + "/routing-rules";

  @Inject
  public RoutingRulesApiResourceBeta(final RoutingRuleStore routingRuleStore,
                                     final RoutingRuleHelper routingRuleHelper)
  {
    super(routingRuleStore, routingRuleHelper);
  }
}
