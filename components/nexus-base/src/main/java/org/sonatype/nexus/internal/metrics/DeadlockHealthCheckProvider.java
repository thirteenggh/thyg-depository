package org.sonatype.nexus.internal.metrics;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;

/**
 * {@link ThreadDeadlockHealthCheck} provider.
 *
 * @since 2.8
 */
@Named("线程死锁检测器")
@Singleton
public class DeadlockHealthCheckProvider
  implements Provider<HealthCheck>
{
  public HealthCheck get() {
    return new ThreadDeadlockHealthCheck();
  }
}
