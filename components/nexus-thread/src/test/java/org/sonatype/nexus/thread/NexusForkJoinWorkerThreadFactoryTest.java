package org.sonatype.nexus.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @since 3.20
 */
public class NexusForkJoinWorkerThreadFactoryTest
{
  private NexusForkJoinWorkerThreadFactory nexusForkJoinWorkerThreadFactory;

  @Test
  public void prefixIsAddedToThread() {
    nexusForkJoinWorkerThreadFactory = new NexusForkJoinWorkerThreadFactory("prefix-test");
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    ForkJoinWorkerThread thread = nexusForkJoinWorkerThreadFactory.newThread(forkJoinPool);
    assertThat(thread.getName(), CoreMatchers.containsString("prefix-test"));
  }

}
