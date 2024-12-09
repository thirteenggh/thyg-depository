package org.sonatype.nexus.repository.maven.api;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.rest.api.SimpleApiRepositoryAdapter;
import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;
import org.sonatype.nexus.repository.routing.RoutingRuleStore;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.repository.types.ProxyType;

/**
 * Adapter to expose maven specific repository configuration for the repositories REST API.
 *
 * @since 3.20
 */
@Named(Maven2Format.NAME)
public class MavenApiRepositoryAdapter
    extends SimpleApiRepositoryAdapter
{
  @Inject
  public MavenApiRepositoryAdapter(final RoutingRuleStore routingRuleStore) {
    super(routingRuleStore);
  }

  @Override
  public AbstractApiRepository adapt(final Repository repository) {
    boolean online = repository.getConfiguration().isOnline();
    String name = repository.getName();
    String url = repository.getUrl();

    switch (repository.getType().toString()) {
      case HostedType.NAME:
        return new MavenHostedApiRepository(name, url, online, getHostedStorageAttributes(repository),
            getCleanupPolicyAttributes(repository), createMavenAttributes(repository));
      case ProxyType.NAME:
        return new MavenProxyApiRepository(name, url, online, getHostedStorageAttributes(repository),
            getCleanupPolicyAttributes(repository), getProxyAttributes(repository),
            getNegativeCacheAttributes(repository), getHttpClientAttributes(repository), getRoutingRuleName(repository),
            createMavenAttributes(repository));
      default:
        return super.adapt(repository);
    }
  }

  private MavenAttributes createMavenAttributes(final Repository repository) {
    String versionPolicy = repository.getConfiguration().attributes("maven").get("versionPolicy", String.class);
    String layoutPolicy = repository.getConfiguration().attributes("maven").get("layoutPolicy", String.class);
    return new MavenAttributes(versionPolicy, layoutPolicy);
  }
}
