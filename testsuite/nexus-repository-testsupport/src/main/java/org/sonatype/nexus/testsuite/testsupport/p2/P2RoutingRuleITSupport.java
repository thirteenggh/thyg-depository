package org.sonatype.nexus.testsuite.testsupport.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.goodies.httpfixture.server.api.Behaviour;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;
import org.sonatype.nexus.testsuite.testsupport.fixtures.RoutingRuleRule;

import org.junit.Rule;

/**
 * Support class for P2 Routing Rule ITs.
 *
 * @since 0.next
 */
public class P2RoutingRuleITSupport extends P2ITSupport
{
  @Inject
  private RoutingRuleStore ruleStore;

  @Rule
  public RoutingRuleRule routingRules = new RoutingRuleRule(() -> ruleStore);

  protected EntityId createBlockedRoutingRule(final String name, final String matcher) {
    return routingRules.create(name, matcher).id();
  }

  protected void attachRuleToRepository(final Repository repository, final EntityId routingRuleId) throws Exception {
    org.sonatype.nexus.repository.config.Configuration configuration = repository.getConfiguration();
    configuration.setRoutingRuleId(routingRuleId);
    repositoryManager.update(configuration);
  }

  protected static class BehaviourSpy
      implements Behaviour
  {
    private static final String REQUEST_URI_PATTERN = "%s?%s";

    private Behaviour delegate;

    private List<String> requestUris = new ArrayList<>();

    public BehaviourSpy(final Behaviour delegate) {
      this.delegate = delegate;
    }

    public List<String> getRequestUris() {
      return requestUris;
    }

    @Override
    public boolean execute(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Map<Object, Object> ctx) throws Exception
    {
      requestUris.add(String.format(REQUEST_URI_PATTERN, request.getRequestURI(), request.getQueryString()));
      return delegate.execute(request, response, ctx);
    }
  }
}
