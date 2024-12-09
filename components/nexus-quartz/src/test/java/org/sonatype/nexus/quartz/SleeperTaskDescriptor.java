package org.sonatype.nexus.quartz;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Descriptor for {@link SleeperTask}.
 */
@Named
@Singleton
public class SleeperTaskDescriptor
    extends TaskDescriptorSupport
{
  static final String TYPE_ID = "sleeper";

  public SleeperTaskDescriptor()
  {
    super(TYPE_ID, SleeperTask.class, "Sleeper test", VISIBLE, EXPOSED);
  }
}
