package org.sonatype.nexus.cleanup.internal.task;

import java.util.function.BooleanSupplier;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.cleanup.service.CleanupService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class CleanupTaskTest
    extends TestSupport
{
  @Mock
  private CleanupService cleanupService;

  private CleanupTask underTest;

  @Before
  public void setup() throws Exception {
    underTest = new CleanupTask(cleanupService);
  }

  @Test
  public void runCleanup() throws Exception {
    underTest.execute();

    verify(cleanupService).cleanup(any(BooleanSupplier.class));
  }
}
