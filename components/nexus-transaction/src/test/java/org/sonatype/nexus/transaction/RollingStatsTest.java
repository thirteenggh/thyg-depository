package org.sonatype.nexus.transaction;

import java.util.concurrent.ThreadLocalRandom;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.goodies.testsupport.concurrent.ConcurrentRunner;

import org.junit.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test rolling stats behaviour.
 */
public class RollingStatsTest
    extends TestSupport
{
  @Test
  public void testConcurrentStats() throws Exception {
    RollingStats underTest = new RollingStats(60_000, MILLISECONDS);

    ConcurrentRunner runner = new ConcurrentRunner(3, 60);
    runner.addTask(100, () -> {
      // randomize where this will land inside the window
      Thread.sleep(ThreadLocalRandom.current().nextInt(1_000));
      underTest.mark();
    });

    runner.go();

    assertThat(underTest.sum(), is(runner.getTaskCount() * runner.getIterations()));
  }

  @Test
  public void testRollingStats() throws Exception {
    RollingStats underTest = new RollingStats(2_000, MILLISECONDS);

    underTest.mark();
    assertThat(underTest.sum(), is(1));
    underTest.mark();
    assertThat(underTest.sum(), is(2));
    underTest.mark();
    assertThat(underTest.sum(), is(3));
    underTest.mark();
    assertThat(underTest.sum(), is(4));

    Thread.sleep(500);

    underTest.mark();
    assertThat(underTest.sum(), is(5));
    underTest.mark();
    assertThat(underTest.sum(), is(6));
    underTest.mark();
    assertThat(underTest.sum(), is(7));
    underTest.mark();
    assertThat(underTest.sum(), is(8));

    Thread.sleep(500);

    underTest.mark();
    assertThat(underTest.sum(), is(9));
    underTest.mark();
    assertThat(underTest.sum(), is(10));
    underTest.mark();
    assertThat(underTest.sum(), is(11));
    underTest.mark();
    assertThat(underTest.sum(), is(12));

    Thread.sleep(500);

    assertThat(underTest.sum(), is(12));

    Thread.sleep(750);

    assertThat(underTest.sum(), is(8));

    Thread.sleep(500);

    assertThat(underTest.sum(), is(4));

    Thread.sleep(500);

    assertThat(underTest.sum(), is(0));
  }
}
