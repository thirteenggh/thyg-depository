package org.sonatype.nexus.repository.npm.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.npm.internal.NpmFormat;
import org.sonatype.nexus.repository.rest.api.SimpleApiRepositoryAdapter;
import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;
import org.sonatype.nexus.repository.types.ProxyType;

/**
 * @since 3.29
 */
@Named(NpmFormat.NAME)
public class NpmApiRepositoryAdapter
    extends SimpleApiRepositoryAdapter
{
  @Inject
  public NpmApiRepositoryAdapter(final RoutingRuleStore routingRuleStore) {
    super(routingRuleStore);
  }

  @Override
  public AbstractApiRepository adapt(final Repository repository) {
    if (ProxyType.NAME.equals(repository.getType().toString())) {
      return new NpmProxyApiRepository(repository.getName(), repository.getUrl(),
          repository.getConfiguration().isOnline(),
          getHostedStorageAttributes(repository),
          getCleanupPolicyAttributes(repository), getProxyAttributes(repository),
          getNegativeCacheAttributes(repository), getHttpClientAttributes(repository), getRoutingRuleName(repository),
          createNpmAttributes(repository));
    }
    return super.adapt(repository);
  }

  private NpmAttributes createNpmAttributes(final Repository repository) {
    Boolean removeNonCataloged = repository.getConfiguration().attributes("npm")
        .get(NpmAttributes.REMOVE_NON_CATALOGED, Boolean.class, Boolean.FALSE);
    return new NpmAttributes(removeNonCataloged);
  }
}
