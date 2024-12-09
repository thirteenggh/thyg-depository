package org.sonatype.nexus.testsuite.testsupport.performance;

import java.util.concurrent.Callable;

import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.pax.exam.NexusPaxExamSupport;

/**
 * Waits for Nexus to be in a calm state.
 */
public class WaitForCalmPeriod implements Callable<Void>
{
  final EventManager eventManager;

  public WaitForCalmPeriod(final EventManager eventManager) {
    this.eventManager = eventManager;
  }

  @Override
  public Void call() throws Exception {
    NexusPaxExamSupport.waitFor(eventManager::isCalmPeriod);
    return null;
  }
}
