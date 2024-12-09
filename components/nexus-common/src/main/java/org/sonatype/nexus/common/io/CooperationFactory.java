package org.sonatype.nexus.common.io;

import org.sonatype.goodies.common.Time;

/**
 * Supplies {@link Cooperation} points.
 *
 * @since 3.14
 */
public interface CooperationFactory
{
  /**
   * Start configuring a new {@link Cooperation} point.
   */
  Builder configure();

  /**
   * Fluent builder for configuring {@link Cooperation} points.
   */
  interface Builder
  {
    /**
     * @param majorTimeout when waiting for the main I/O request
     */
    Builder majorTimeout(Time majorTimeout);

    /**
     * @param minorTimeout when waiting for any I/O dependencies
     */
    Builder minorTimeout(Time minorTimeout);

    /**
     * @param threadsPerKey limits the threads waiting under each key
     */
    Builder threadsPerKey(int threadsPerKey);

    /**
     * Builds a new {@link Cooperation} point with this configuration.
     *
     * @param id unique identifier for this cooperation point
     */
    Cooperation build(String id);
  }
}
