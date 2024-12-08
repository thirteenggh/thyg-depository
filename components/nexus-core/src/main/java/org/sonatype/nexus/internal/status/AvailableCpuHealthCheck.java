package org.sonatype.nexus.internal.status;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.annotations.VisibleForTesting;

/**
 * Health check that indicates if the available JVM reported CPU count is below the recommended threshold.
 * <p>
 * The {@see Runtime#availableProcessors} API used to check the number of available processors may return different
 * results during the lifetime of the JVM, but we will always consider it not healthy if the CPU is dynamically reduced
 * below threshold.
 *
 * @since 3.17
 */
@Named("可用CPU")
@Singleton
public class AvailableCpuHealthCheck
    extends HealthCheckComponentSupport
{
  /**
   * Minimum recommended CPU Count
   */
  static final int MIN_RECOMMENDED_CPU_COUNT = 4;

  private static final String HEALTHY_MESSAGE = "主机系统最多为应用程序分配%d个内核。";

  private static final String UNHEALTHY_MESSAGE = HEALTHY_MESSAGE + "建议至少%d。";

  private Runtime runtime;

  private Runtime getRuntime() {
    if (runtime == null) {
      runtime = Runtime.getRuntime();
    }
    return runtime;
  }

  @VisibleForTesting
  protected void setRuntime(Runtime runtime) {
    this.runtime = runtime;
  }

  @Override
  protected Result check() {
    int available = getRuntime().availableProcessors();

    if (MIN_RECOMMENDED_CPU_COUNT > available) {
      return Result.unhealthy(UNHEALTHY_MESSAGE, available, MIN_RECOMMENDED_CPU_COUNT);
    }

    return Result.healthy(HEALTHY_MESSAGE, available);
  }
}
