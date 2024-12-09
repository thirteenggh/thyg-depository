package org.sonatype.nexus.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Nexus {@link ForkJoinWorkerThreadFactory}.
 * @since 3.20
 */
public class NexusForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory
{
  private final String jobPrefix;

  public NexusForkJoinWorkerThreadFactory(final String jobPrefix) {
    this.jobPrefix = checkNotNull(jobPrefix);
  }

  @Override
  public ForkJoinWorkerThread newThread(final ForkJoinPool pool) {
    final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
    worker.setName(jobPrefix + worker.getPoolIndex());
    return worker;
  }
}
