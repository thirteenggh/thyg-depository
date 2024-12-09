package org.sonatype.nexus.scheduling.internal;

import org.sonatype.nexus.scheduling.TaskSupport;

/**
 * Simple task.
 *
 * @see SimpleTaskDescriptor
 */
public class SimpleTask
    extends TaskSupport
{
  @Override
  protected Object execute() throws Exception {
    return "simple-result";
  }

  @Override
  public String getMessage() {
    return "simple-message";
  }
}
