package org.sonatype.nexus.quartz;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.sonatype.nexus.scheduling.TaskSupport;

/**
 * Simple sleeper task that is not cancelable.
 */
@Named
public class SleeperTask
    extends TaskSupport
{
  static final String RESULT_KEY = "result";

  static final String SLEEP_MILLIS_KEY = "sleepMillis";

  static CountDownLatch meWait;

  static CountDownLatch youWait;

  static Exception exception;

  static void reset() {
    meWait = new CountDownLatch(1);
    youWait = new CountDownLatch(1);
    exception = null;
  }

  @Override
  protected String execute() throws Exception {
    youWait.countDown(); // task signals "started" to test

    while (!meWait.await(1L, TimeUnit.SECONDS)) { // test signals "finish" to this task
      doTheWork();
    }

    if (exception != null) {
      throw exception;
    }

    return getConfiguration().getString(RESULT_KEY);
  }

  @Override
  public String getMessage() {
    return "Message is:" + getConfiguration().getString(RESULT_KEY);
  }

  protected void doTheWork() throws Exception {
    Thread.sleep(sleepMillis()); // kinda working
  }

  protected long sleepMillis() {
    return getConfiguration().getLong(SLEEP_MILLIS_KEY, 10L);
  }
}
