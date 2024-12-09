package org.sonatype.nexus.internal.orient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.transaction.RetryController;

import com.codahale.metrics.health.HealthCheck;

import static com.codahale.metrics.health.HealthCheck.Result.healthy;
import static com.codahale.metrics.health.HealthCheck.Result.unhealthy;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Inform on the health of transactions; reports if any have an excessive number of retries.
 *
 * @since 3.16
 */
@Named("事务")
@Singleton
public class TransactionHealthCheck
    extends HealthCheck
{
  private final RetryController retryController;

  @Inject
  public TransactionHealthCheck(final RetryController retryController) {
    this.retryController = checkNotNull(retryController);
  }

  @Override
  protected Result check() {
    long excessiveRetriesInLastHour = retryController.excessiveRetriesInLastHour();
    if (excessiveRetriesInLastHour > 0) {
      return unhealthy("%d个事务%s在过去一小时内重试次数过多",
          excessiveRetriesInLastHour, excessiveRetriesInLastHour == 1 ? "" : "s");
    }
    else {
      return healthy();
    }
  }
}
