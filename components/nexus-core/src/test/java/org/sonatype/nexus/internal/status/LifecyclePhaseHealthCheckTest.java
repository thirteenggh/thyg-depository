package org.sonatype.nexus.internal.status;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class LifecyclePhaseHealthCheckTest
    extends TestSupport
{
  @Mock
  ManagedLifecycleManager lifecycleManager;

  @Test
  public void TasksPhaseYieldsHealthy() {
    when(lifecycleManager.getCurrentPhase()).thenReturn(Phase.TASKS);
    LifecyclePhaseHealthCheck phaseHealthCheck = new LifecyclePhaseHealthCheck(lifecycleManager);

    assertThat(phaseHealthCheck.check().isHealthy(), is(true));
  }

  @Test
  public void StoragePhaseYieldsUnhealthy() {
    when(lifecycleManager.getCurrentPhase()).thenReturn(Phase.STORAGE);
    LifecyclePhaseHealthCheck phaseHealthCheck = new LifecyclePhaseHealthCheck(lifecycleManager);

    assertThat(phaseHealthCheck.check().isHealthy(), is(false));
  }
}
