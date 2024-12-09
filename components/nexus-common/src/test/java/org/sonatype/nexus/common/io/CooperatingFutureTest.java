package org.sonatype.nexus.common.io;

import java.util.Random;
import java.util.concurrent.locks.LockSupport;

import org.sonatype.goodies.common.Time;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.io.CooperationFactorySupport.Config;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class CooperatingFutureTest
    extends TestSupport
{
  @Mock
  Config config;

  @Test
  public void downloadTimeoutsAreStaggered() {
    CooperatingFuture<String> cooperatingFuture = new CooperatingFuture<>("testKey", config);

    Random random = new Random();
    long[] downloadTimeMillis = new long[10];
    long expectedGap = 200;

    downloadTimeMillis[0] = System.currentTimeMillis(); // first download
    for (int i = 1; i < downloadTimeMillis.length; i++) {

      // random sleep representing some client-side work
      LockSupport.parkNanos(Time.millis(random.nextInt((int) expectedGap)).toNanos());

      // staggered sleep should bring us close to the expected gap
      LockSupport.parkNanos(cooperatingFuture.staggerTimeout(Time.millis(expectedGap)).toNanos());

      downloadTimeMillis[i] = System.currentTimeMillis(); // next download
    }

    for (int i = 1; i < downloadTimeMillis.length; i++) {
      long actualGap = downloadTimeMillis[i] - downloadTimeMillis[i - 1];
      assertThat(actualGap, allOf(greaterThanOrEqualTo(expectedGap - 10), lessThanOrEqualTo(expectedGap + 10)));
    }
  }
}
