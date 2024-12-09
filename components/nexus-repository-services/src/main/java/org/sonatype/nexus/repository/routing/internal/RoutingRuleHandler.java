package org.sonatype.nexus.repository.routing.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.routing.RoutingRuleHelper;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A handler which validates that the request is allowed by the RoutingRule assigned to the repository.
 *
 * @since 3.16
 */
@Named
@Singleton
public class RoutingRuleHandler
    extends ComponentSupport
    implements Handler, org.sonatype.nexus.repository.routing.RoutingRuleHandler
{
  static final String PATH_IS_BLOCKED = "Routing rules block the requested item from this repository";

  private final RoutingRuleHelper routingRuleHelper;

  @Inject
  public RoutingRuleHandler(final RoutingRuleHelper routingRuleHelper) {
    this.routingRuleHelper = checkNotNull(routingRuleHelper);
  }

  @Override
  public Response handle(final Context context) throws Exception {
    if (routingRuleHelper.isAllowed(context.getRepository(), path(context.getRequest()))) {
      return context.proceed();
    }
    return HttpResponses.forbidden(PATH_IS_BLOCKED);
  }

  private String path(final Request request) {
    if (request.getParameters().size() == 0) {
      return request.getPath();
    }
    StringBuilder sb = new StringBuilder();
    request.getParameters().forEach(entry -> sb.append('&').append(entry.getKey()).append('=').append(entry.getValue()));
    sb.replace(0, 1, "?");
    return request.getPath() + sb;
  }
}
