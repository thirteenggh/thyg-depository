package org.sonatype.nexus.logging.task;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class TaskLoggerHelperTest
    extends TestSupport
{
  @Mock
  private Logger logger;

  @Mock
  private TaskLogger taskLogger;

  @Test
  public void testHelper() {
    assertNull(TaskLoggerHelper.get());

    TaskLoggerHelper.start(taskLogger);
    assertNotNull(TaskLoggerHelper.get());

    TaskLoggingEvent event = new TaskLoggingEvent(logger, "message");
    TaskLoggerHelper.progress(event);
    verify(taskLogger).progress(event);

    TaskLoggerHelper.finish();
    assertNull(TaskLoggerHelper.get());
  }
}
