package org.sonatype.nexus.rapture.internal.state;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rapture.StateContributor;
import org.sonatype.nexus.rapture.internal.HealthCheckCacheManager;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * State contributor that indicates if any of the system Health checks are currently failing
 *
 * @since 3.16
 */
@Named
@Singleton
public class HealthCheckStateContributor
    extends ComponentSupport
    implements StateContributor
{
  @VisibleForTesting
  protected static final String HC_FAILED_KEY = "health_checks_failed";

  private HealthCheckCacheManager healthCheckCacheManager;

  @Inject
  public HealthCheckStateContributor(final HealthCheckCacheManager healthCheckCacheManager) {
    this.healthCheckCacheManager = checkNotNull(healthCheckCacheManager);
  }

  @Nullable
  @Override
  public Map<String, Object> getState() {
    return ImmutableMap.of(HC_FAILED_KEY,
        healthCheckCacheManager.getResults().values().stream().anyMatch(result -> !result.isHealthy()));
  }
}
