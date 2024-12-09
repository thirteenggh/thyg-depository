package org.sonatype.nexus.quartz;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Descriptor for {@link SleeperCancelableTask}.
 */
@Named
@Singleton
public class SleeperCancelableTaskDescriptor
    extends TaskDescriptorSupport
{
  static final String TYPE_ID = "sleeperCancelable";

  public SleeperCancelableTaskDescriptor()
  {
    super(TYPE_ID, SleeperCancelableTask.class, "Sleeper cancelable test", VISIBLE, EXPOSED);
  }
}
