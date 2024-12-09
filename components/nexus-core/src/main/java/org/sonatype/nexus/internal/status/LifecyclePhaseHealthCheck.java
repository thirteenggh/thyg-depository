package org.sonatype.nexus.internal.status;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * Health check that indicates if Nexus is in the correct (i.e. final) phase
 *
 * @since 3.16
 */
@Named("生命周期阶段")
@Singleton
public class LifecyclePhaseHealthCheck
    extends HealthCheckComponentSupport
{
  private final Phase finalPhase;

  private final ManagedLifecycleManager lifecycleManager;

  @Inject
  public LifecyclePhaseHealthCheck(final ManagedLifecycleManager lifecycleManager) {
    this.lifecycleManager = checkNotNull(lifecycleManager);
    finalPhase = Phase.values()[Phase.values().length - 1];
  }

  @Override
  protected Result check() {
    return finalPhase == lifecycleManager.getCurrentPhase() ? Result.healthy() : Result.unhealthy(unhealthyMsg());
  }

  private String unhealthyMsg() {
    return format("Nexus 的当前生命周期阶段为%s，但期望为%s",
        lifecycleManager.getCurrentPhase().name(), finalPhase.name());
  }

}
