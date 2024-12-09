package org.sonatype.nexus.quartz;

import javax.inject.Named;

import org.sonatype.nexus.scheduling.Cancelable;
import org.sonatype.nexus.scheduling.CancelableHelper;

/**
 * Simple sleeper task that is cancelable.
 */
@Named
public class SleeperCancelableTask
    extends SleeperTask
    implements Cancelable
{
  @Override
  protected void doTheWork() throws Exception {
    super.doTheWork();
    CancelableHelper.checkCancellation();
  }
}
