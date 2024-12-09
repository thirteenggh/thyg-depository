package org.sonatype.nexus.repository.npm.internal;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Named;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.manager.RepositoryUpdatedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Handles invalidating NPM proxy cache when the URL for the repository changes.
 *
 * @since 3.21
 */
@Named
@Facet.Exposed
public class NpmProxyCacheInvalidatorFacetImpl
    extends FacetSupport
{
  @Subscribe
  @AllowConcurrentEvents
  protected void on(final RepositoryUpdatedEvent event) {
    final Repository repository = event.getRepository();

    repository.optionalFacet(NpmProxyFacet.class).ifPresent(npm -> {
      if (!Objects.equals(getRemoteUrl(repository.getConfiguration()), getRemoteUrl(event.getOldConfiguration()))) {
        npm.invalidateProxyCaches();
      }
    });
  }

  private static Object getRemoteUrl(final Configuration configuration) {
    return Optional.ofNullable(configuration.getAttributes().get("proxy")).map(proxy -> {
      if (proxy instanceof Map) {
        return proxy.get("remoteUrl");
      }
      return null;
    }).orElse(null);
  }
}
