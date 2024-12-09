package org.sonatype.nexus.internal.status;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FreezeRequest;
import org.sonatype.nexus.common.app.FreezeService;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.text.Strings2.isBlank;

/**
 * Health check that warns when the application is read-only.
 *
 * @since 3.16
 */
@Named("只读探测器")
@Singleton
public class ReadOnlyHealthCheck
    extends HealthCheckComponentSupport
{
  private final FreezeService freezeService;

  @Inject
  public ReadOnlyHealthCheck(final FreezeService freezeService) {
    this.freezeService = checkNotNull(freezeService);
  }

  @Override
  protected Result check() {
    return freezeService.currentFreezeRequests().stream()
        .findFirst()
        .map(this::describe)
        .map(Result::unhealthy)
        .orElse(Result.healthy());
  }

  private String describe(final FreezeRequest request) {
    String description = "由" + request.frozenBy().orElse("SYSTEM");
    if (!isBlank(request.reason())) {
      description += "设为只读，原因: " + request.reason();
    }
    return description;
  }
}
