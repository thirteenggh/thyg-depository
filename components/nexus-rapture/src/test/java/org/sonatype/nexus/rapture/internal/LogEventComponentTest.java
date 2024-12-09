package org.sonatype.nexus.rapture.internal;

import org.sonatype.nexus.rapture.internal.logging.LogEventComponent;
import org.sonatype.nexus.rapture.internal.logging.LogEventXO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerFactory.class)
public class LogEventComponentTest
{
  private LogEventComponent underTest;

  @Test
  public void testEnabledLogging() {
    underTest = new LogEventComponent(true);

    mockStatic(LoggerFactory.class);
    when(LoggerFactory.getLogger("org.something")).thenReturn(mock(Logger.class));

    underTest.recordEvent(createLogEvent());

    verifyStatic();
    LoggerFactory.getLogger("org.something");
  }

  @Test
  public void testDisabledLogging() {
    underTest = new LogEventComponent(false);

    mockStatic(LoggerFactory.class);

    underTest.recordEvent(createLogEvent());

    PowerMockito.verifyNoMoreInteractions(LoggerFactory.class);
  }

  private LogEventXO createLogEvent() {
    LogEventXO logEventXO = new LogEventXO();
    logEventXO.setLogger("org.something");
    logEventXO.setLevel("debug");
    return logEventXO;
  }
}
