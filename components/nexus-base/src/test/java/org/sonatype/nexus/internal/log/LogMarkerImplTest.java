package org.sonatype.nexus.internal.log;

import java.util.EnumSet;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.log.LogManager;
import org.sonatype.nexus.common.log.LogMarkInsertedEvent;
import org.sonatype.nexus.common.log.LoggerLevel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link LogMarkerImpl}.
 */
public class LogMarkerImplTest
    extends TestSupport
{
  private static final String LOG_NAME = LogMarkerImpl.class.getName();

  @Mock
  private LogManager logManager;

  @Mock
  private EventManager eventManager;

  private LogMarkerImpl logMarker;

  @Before
  public void setUp() {
    logMarker = new LogMarkerImpl(logManager, eventManager);
    when(logManager.getLoggerEffectiveLevel(LOG_NAME)).thenReturn(LoggerLevel.INFO);
  }

  @Test
  public void testMarkLog_EmitEvent() {
    logMarker.markLog("test");
    ArgumentCaptor<LogMarkInsertedEvent> argCaptor = ArgumentCaptor.forClass(LogMarkInsertedEvent.class);
    verify(eventManager).post(argCaptor.capture());
    assertThat(argCaptor.getValue().getMessage(), is("test"));
  }

  @Test
  public void testMarkLog_EnsureLogLevel() {
    for (LoggerLevel level : EnumSet.complementOf(EnumSet.of(LoggerLevel.DEFAULT))) {
      reset(logManager);
      when(logManager.getLoggerEffectiveLevel(LOG_NAME)).thenReturn(level);
      logMarker.markLog("test");
      try {
        if (EnumSet.of(LoggerLevel.OFF, LoggerLevel.ERROR, LoggerLevel.WARN).contains(level)) {
          verify(logManager).setLoggerLevel(LOG_NAME, LoggerLevel.INFO);
        }
        else {
          verify(logManager, never()).setLoggerLevel(anyObject(), anyObject());
        }
      }
      catch (AssertionError e) {
        throw new AssertionError("Mishandled log level " + level, e);
      }
    }
  }
}
