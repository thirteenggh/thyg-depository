package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Provider;

import org.sonatype.nexus.repository.routing.RoutingMode;
import org.sonatype.nexus.repository.routing.RoutingRule;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;

import org.junit.rules.ExternalResource;

public class RoutingRuleRule
    extends ExternalResource
{
  private Provider<RoutingRuleStore> ruleStoreProvider;

  List<RoutingRule> rules = new ArrayList<>();

  public RoutingRuleRule(final Provider<RoutingRuleStore> ruleStoreProvider) {
    this.ruleStoreProvider = ruleStoreProvider;
  }

  /**
   * Create a RoutingRule with mode block and a dummy description
   */
  public RoutingRule create(final String name, final String pattern) {
    final RoutingRuleStore routingRuleStore = ruleStoreProvider.get();
    return create(routingRuleStore.newRoutingRule()
        .name(name)
        .description("some description")
        .mode(RoutingMode.BLOCK)
        .matchers(Collections.singletonList(pattern))
    );
  }

  public RoutingRule create(final RoutingRule routingRule) {
    RoutingRule result = ruleStoreProvider.get().create(routingRule);
    rules.add(result);
    return result;
  }

  @Override
  protected void after() {
    for (RoutingRule rule : rules) {
      ruleStoreProvider.get().delete(rule);
    }
  }
}
