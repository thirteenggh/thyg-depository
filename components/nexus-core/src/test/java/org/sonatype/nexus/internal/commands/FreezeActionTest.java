package org.sonatype.nexus.internal.commands;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.FreezeService;
import org.sonatype.nexus.internal.commands.FreezeAction.Mode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class FreezeActionTest
    extends TestSupport
{

  FreezeAction underTest;

  @Mock
  FreezeService freezeService;

  @Before
  public void setUp() throws Exception {
    underTest = new FreezeAction(freezeService);
  }

  @Test
  public void executeFreeze() throws Exception {
    // defaults to 'enable' (read: freeze)
    underTest.execute();

    verify(freezeService).requestFreeze(isA(String.class));
    verify(freezeService, never()).cancelFreeze();
  }

  @Test
  public void executeRelease() throws Exception {
    underTest.mode = Mode.release;

    underTest.execute();

    verify(freezeService, never()).requestFreeze(isA(String.class));
    verify(freezeService).cancelFreeze();
  }
}
