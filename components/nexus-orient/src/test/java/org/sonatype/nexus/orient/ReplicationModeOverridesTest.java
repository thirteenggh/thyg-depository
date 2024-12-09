package org.sonatype.nexus.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.goodies.testsupport.concurrent.ConcurrentRunner;

import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.sonatype.nexus.orient.ReplicationModeOverrides.clearReplicationModeOverrides;
import static org.sonatype.nexus.orient.ReplicationModeOverrides.dontWaitForReplicationResults;
import static org.sonatype.nexus.orient.ReplicationModeOverrides.shouldWaitForReplicationResults;

public class ReplicationModeOverridesTest
    extends TestSupport
{
  @Test
  public void overrideIsPerThread() throws Exception {
    ConcurrentRunner runner = new ConcurrentRunner(1, 60);

    runner.addTask(10, () -> {
      assertThat(shouldWaitForReplicationResults(), is(empty()));
    });

    runner.addTask(10, () -> {
      dontWaitForReplicationResults();
      try {
        assertThat(shouldWaitForReplicationResults(), is(of(FALSE)));
      }
      finally {
        clearReplicationModeOverrides();
      }
    });

    runner.go();

    assertThat(runner.getRunInvocations(), is(runner.getTaskCount() * runner.getIterations()));
  }
}
