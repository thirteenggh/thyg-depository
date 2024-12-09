package org.sonatype.nexus.internal.status;

import com.codahale.metrics.health.HealthCheck.Result;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailableCpuHealthCheckTest
{

  private AvailableCpuHealthCheck underTest;

  @Before
  public void setup() {
    underTest = new AvailableCpuHealthCheck();
  }

  @Test
  public void testCheck_plentyOfCores() {
    Runtime runtime = mock(Runtime.class);
    when(runtime.availableProcessors()).thenReturn(86);
    underTest.setRuntime(runtime);
    Result result = underTest.check();
    assertThat(result.isHealthy(), is(true));
    assertThat(result.getMessage(), is("The host system is allocating a maximum of 86 cores to the application."));
  }

  @Test
  public void testCheck_notEnoughCores() {
    Runtime runtime = mock(Runtime.class);
    when(runtime.availableProcessors()).thenReturn(1);
    underTest.setRuntime(runtime);
    Result result = underTest.check();
    assertThat(result.isHealthy(), is(false));
    assertThat(result.getMessage(), is("The host system is allocating a maximum of 1 cores to the application." +
        " A minimum of " + AvailableCpuHealthCheck.MIN_RECOMMENDED_CPU_COUNT + " is recommended."));
  }
}
