package org.sonatype.nexus.thread;

import java.util.concurrent.ForkJoinPool;

/**
 * @since 3.20
 */
public class NexusForkJoinPoolFactory
{
  private NexusForkJoinPoolFactory() {
    // static utility class
  }

  // Copied from the constant in {@link ForkJoinPool} because the constant is not visible.
  private static final int MAX_CAP = 0x7fff;

  public static ForkJoinPool createForkJoinPool(final String threadNamePrefix) {
    NexusForkJoinWorkerThreadFactory nexusForkJoinWorkerThreadFactory =
        new NexusForkJoinWorkerThreadFactory(threadNamePrefix);
    return new ForkJoinPool(Math.min(MAX_CAP, Runtime.getRuntime().availableProcessors()),
        nexusForkJoinWorkerThreadFactory, null, false);
  }
}
