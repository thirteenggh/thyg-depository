package org.sonatype.nexus.testsuite.testsupport.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;

/**
 * Tests {@link LoadExecutor}
 */
public class LoadExecutorTest
{
  @Test(expected = IllegalStateException.class)
  public void taskExceptionsArePropagated() throws Exception {
    final List<Callable<?>> tasks = new ArrayList<>();
    tasks.add(new Callable<Void>()
    {
      @Override
      public Void call() throws Exception {
        throw new IllegalStateException("expected");
      }
    });

    new LoadExecutor(tasks, 1, 10).callTasks();
  }

  @Test(expected = AssertionError.class)
  public void taskAssertionErrorsArePropagated() throws Exception {
    final List<Callable<?>> tasks = new ArrayList<>();
    tasks.add(new Callable<Void>()
    {
      @Override
      public Void call() throws Exception {
        throw new AssertionError("expected");
      }
    });

    new LoadExecutor(tasks, 1, 10).callTasks();
  }

  @SuppressWarnings("java:S2699") // sonar wants assertions, but in this case seems best to let an exception bubble up
  @Test
  public void noFailureTestDoesActuallyStop() throws Exception {
    final List<Callable<?>> tasks = new ArrayList<>();
    tasks.add(new Callable<Void>()
    {
      @Override
      public Void call() throws Exception {
        // This task is always successful
        return null;
      }
    });

    new LoadExecutor(tasks, 1, 5).callTasks();
  }
}
