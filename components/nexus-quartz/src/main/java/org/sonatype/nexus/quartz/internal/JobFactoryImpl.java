package org.sonatype.nexus.quartz.internal;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;

import org.eclipse.sisu.BeanEntry;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sisu-aware {@link JobFactory}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class JobFactoryImpl
    extends ComponentSupport
    implements JobFactory
{
  // TODO: mediator pattern here may be better to avoid over-iterating?

  private final Iterable<BeanEntry<Named, Job>> entries;

  @Inject
  public JobFactoryImpl(final Iterable<BeanEntry<Named, Job>> entries) {
    this.entries = checkNotNull(entries);
  }

  @Override
  public Job newJob(final TriggerFiredBundle bundle, final Scheduler scheduler) throws SchedulerException {
    Class<? extends Job> type = bundle.getJobDetail().getJobClass();
    log.debug("New job: {}", type);

    BeanEntry<Named, Job> beanEntry = locate(type);
    if (beanEntry == null) {
      throw new SchedulerException("Missing Job component for type: " + type.getName());
    }

    return beanEntry.getProvider().get(); // to support non-singletons
  }

  @Nullable
  private BeanEntry<Named, Job> locate(final Class<? extends Job> jobType) {
    for (BeanEntry<Named, Job> jobEntry : entries) {
      if (jobEntry.getImplementationClass().equals(jobType)) {
        return jobEntry;
      }
    }
    return null;
  }
}
