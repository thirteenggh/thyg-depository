package org.sonatype.nexus.pax.logging;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.logging.task.TaskLogger;
import org.sonatype.nexus.logging.task.TaskLoggerHelper;
import org.sonatype.nexus.logging.task.TaskLoggingEvent;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.slf4j.MDC;
import org.slf4j.Marker;

import static ch.qos.logback.core.spi.FilterReply.DENY;
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.sonatype.nexus.logging.task.TaskLogger.LOGBACK_TASK_DISCRIMINATOR_ID;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.INTERNAL_PROGRESS;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.NEXUS_LOG_ONLY;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.PROGRESS;

public class TaskLogsFilterTest
    extends TestSupport
{
  private static final String TEST_MESSAGE = "test message";

  private static final Object[] TEST_ARGS = new Object[]{"x"};

  @Mock
  private TaskLogger taskLogger;

  private TaskLogsFilter taskLogsFilter;

  @Before
  public void setUp() {
    taskLogsFilter = new TaskLogsFilter();
  }

  @After
  public void tearDown() {
    MDC.remove(LOGBACK_TASK_DISCRIMINATOR_ID);
    if (TaskLoggerHelper.get() != null) {
      TaskLoggerHelper.finish();
    }
  }

  @Test
  public void testNotATask() {
    // not a task
    MDC.remove(LOGBACK_TASK_DISCRIMINATOR_ID);
    assertThat(taskLogsFilter.decide(eventWithMarkerOf(null)), equalTo(DENY));
  }

  @Test
  public void testIsANexusLog() {
    startTask();
    assertThat(taskLogsFilter.decide(eventWithMarkerOf(NEXUS_LOG_ONLY)), equalTo(DENY));
  }

  @Test
  public void testIsInternalProgress() {
    startTask();
    assertThat(taskLogsFilter.decide(eventWithMarkerOf(INTERNAL_PROGRESS)), equalTo(DENY));
  }

  @Test
  public void testIsProgress() {
    startTask();
    assertThat(taskLogsFilter.decide(eventWithMarkerOf(PROGRESS)), equalTo(NEUTRAL));
    assertNotNull(TaskLoggerHelper.get());

    ArgumentCaptor<TaskLoggingEvent> argumentCaptor = ArgumentCaptor.forClass(TaskLoggingEvent.class);
    verify(taskLogger).progress(argumentCaptor.capture());
    TaskLoggingEvent taskLoggingEvent = argumentCaptor.getValue();
    assertNotNull(taskLoggingEvent);
    assertThat(taskLoggingEvent.getMessage(), equalTo(TEST_MESSAGE));
    assertThat(taskLoggingEvent.getArgumentArray(), equalTo(TEST_ARGS));
  }

  @Test
  public void testNotProgress() {
    startTask();
    assertThat(taskLogsFilter.decide(eventWithMarkerOf(null)), equalTo(NEUTRAL));
    assertNotNull(TaskLoggerHelper.get());
  }

  private void startTask() {
    // default is a task
    MDC.put(LOGBACK_TASK_DISCRIMINATOR_ID, "taskId");

    TaskLoggerHelper.start(taskLogger);
  }

  private ILoggingEvent eventWithMarkerOf(final Marker marker) {
    LoggingEvent event = new LoggingEvent();
    event.setMessage(TEST_MESSAGE);
    event.setMarker(marker);
    event.setArgumentArray(TEST_ARGS);
    return event;
  }
}
