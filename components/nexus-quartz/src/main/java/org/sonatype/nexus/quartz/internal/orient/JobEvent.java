package org.sonatype.nexus.quartz.internal.orient;

/**
 * Job event.
 *
 * @since 3.1
 */
public interface JobEvent
{
  boolean isLocal();

  JobDetailEntity getJob();
}
