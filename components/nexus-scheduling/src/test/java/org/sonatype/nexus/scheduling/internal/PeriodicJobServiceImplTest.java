package org.sonatype.nexus.scheduling.internal;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.scheduling.PeriodicJobService.PeriodicJob;

import org.awaitility.Awaitility;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link PeriodicJobServiceImpl}
 */
public class PeriodicJobServiceImplTest
    extends TestSupport
{
  private PeriodicJobServiceImpl service;

  @Before
  public void setUp() {
    service = new PeriodicJobServiceImpl();
  }

  private boolean isRunning() {
    try {
      service.schedule(() -> {} , 60).cancel();
      return true;
    }
    catch (IllegalStateException | NullPointerException e) {
      return false;
    }
  }

  @SuppressWarnings("java:S2699") // sonar doesn't detect awaitility assertions https://jira.sonarsource.com/browse/SONARJAVA-3334
  @Test
  public void numberIncrementingTask() throws Exception {
    service.startUsing();

    final AtomicInteger counter = new AtomicInteger();

    PeriodicJob schedule = service.schedule(counter::incrementAndGet, 1);

    Awaitility.await().atMost(2, TimeUnit.SECONDS).until(() -> counter.get() > 0);

    schedule.cancel();

    service.stopUsing();
  }

  @Test
  public void testConditionalLifecycle() throws Exception {
    assertThat(isRunning(), is(false));
    service.startUsing();
    assertThat(isRunning(), is(true));
    service.startUsing();
    assertThat(isRunning(), is(true));
    service.stopUsing();
    assertThat(isRunning(), is(true));
    service.stopUsing();
    assertThat(isRunning(), is(false));
  }

  @Test(expected = IllegalStateException.class)
  public void testStopUsing_InvalidPrecondition() throws Exception {
    service.stopUsing();
  }
}
