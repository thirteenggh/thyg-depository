package org.sonatype.nexus.repository.rest.internal.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.routing.RoutingRuleHelper;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

import static org.sonatype.nexus.repository.rest.internal.resources.RoutingRulesApiResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

@Named
@Singleton
@Path(RESOURCE_URI)
public class RoutingRulesApiResourceV1
  extends RoutingRulesApiResource
{
  public static final String RESOURCE_URI = V1_API_PREFIX + "/routing-rules";

  @Inject
  public RoutingRulesApiResourceV1(final RoutingRuleStore routingRuleStore,
                                   final RoutingRuleHelper routingRuleHelper)
  {
    super(routingRuleStore, routingRuleHelper);
  }
}
