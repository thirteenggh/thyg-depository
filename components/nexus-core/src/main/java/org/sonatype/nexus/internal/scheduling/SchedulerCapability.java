package org.sonatype.nexus.internal.scheduling;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.capability.CapabilitySupport;
import org.sonatype.nexus.common.template.TemplateParameters;
import org.sonatype.nexus.scheduling.spi.SchedulerSPI;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Scheduler capability.
 *
 * @since 3.0
 */
@Named(SchedulerCapabilityDescriptor.TYPE_ID)
public class SchedulerCapability
    extends CapabilitySupport<SchedulerCapabilityConfiguration>
{
  private final SchedulerSPI scheduler;

  private boolean pausedByUs = false;

  @Inject
  public SchedulerCapability(final SchedulerSPI scheduler) {
    this.scheduler = checkNotNull(scheduler);
  }

  @Override
  protected SchedulerCapabilityConfiguration createConfig(final Map<String, String> properties) throws Exception {
    return new SchedulerCapabilityConfiguration(properties);
  }

  @Override
  protected void onActivate(final SchedulerCapabilityConfiguration config) throws Exception {
    if (pausedByUs) {
      pausedByUs = false;
      scheduler.resume();
    }
  }

  @Override
  protected void onPassivate(final SchedulerCapabilityConfiguration config) throws Exception {
    pausedByUs = true;
    scheduler.pause();
  }

  @Override
  protected String renderDescription() throws Exception {
    return scheduler.renderStatusMessage();
  }

  @Override
  protected String renderStatus() throws Exception {
    return render(SchedulerCapabilityDescriptor.TYPE_ID + "-status.vm", new TemplateParameters()
            .set("detail", scheduler.renderDetailMessage())
    );
  }
}
