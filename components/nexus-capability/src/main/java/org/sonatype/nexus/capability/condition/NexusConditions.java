package org.sonatype.nexus.capability.condition;

import org.sonatype.nexus.capability.Condition;

/**
 * Factory of {@link Condition}s related to Nexus.
 *
 * @since capabilities 2.0
 */
public interface NexusConditions
{
  /**
   * Creates a new condition that is satisfied when Nexus is active (started but not stopped).
   *
   * @return created condition
   */
  Condition active();
}
