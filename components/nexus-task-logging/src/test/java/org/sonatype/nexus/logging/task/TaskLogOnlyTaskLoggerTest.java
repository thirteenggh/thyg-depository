package org.sonatype.nexus.logging.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.MDC;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.verify;
import static org.sonatype.nexus.logging.task.SeparateTaskLogTaskLogger.TASK_LOG_LOCATION_PREFIX;
import static org.sonatype.nexus.logging.task.TaskLogger.TASK_LOG_ONLY_MDC;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.NEXUS_LOG_ONLY;

@RunWith(MockitoJUnitRunner.class)
public class TaskLogOnlyTaskLoggerTest
{
  @Mock
  private Logger log;

  @Mock
  private TaskLogInfo taskLogInfo;

  @InjectMocks
  private TaskLogOnlyTaskLogger taskLogOnlyTaskLogger;

  @Test
  public void mdcShouldContainTaskLogOnlyKey() {

    assertThat(MDC.get(TASK_LOG_ONLY_MDC), equalTo("true"));
  }

  @Test
  public void shouldWriteLogLocationToNexusLog() {
    taskLogOnlyTaskLogger.writeLogFileNameToNexusLog();

    verify(log).info(eq(NEXUS_LOG_ONLY), startsWith(TASK_LOG_LOCATION_PREFIX));
    assertThat(MDC.get(TASK_LOG_ONLY_MDC), equalTo("true"));
  }
}
