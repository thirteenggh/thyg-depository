package org.sonatype.nexus.logging.task;

import org.junit.Test;
import org.slf4j.Logger;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.PROGRESS;

public class ProgressLogIntervalHelperTest
{

  @Test
  public void intervalElapsed() throws InterruptedException {
    Logger logger = mock(Logger.class);
    String arg = "arg";
    Object[] argArray = {arg};

    ProgressLogIntervalHelper underTest = new ProgressLogIntervalHelper(logger, 1);

    // on immediate call interval will not have elapased so the logger should not be hit
    underTest.info("Test 1", arg);
    verify(logger, never()).info(PROGRESS, "Test 1", argArray);

    // sleep for 1 second
    sleep(1100);

    // invoke after interval elapsed and now logger should have been hit
    underTest.info("Test 2", arg);
    verify(logger).info(PROGRESS, "Test 2", argArray);
  }

}
