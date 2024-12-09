package org.sonatype.nexus.capability.condition;

import org.sonatype.nexus.capability.Condition;

/**
 * Central access point for built in {@link Condition}s.
 *
 * @since capabilities 2.0
 */
public interface Conditions
{
  /**
   * Access to logical conditions.
   *
   * @return logical conditions factory
   */
  LogicalConditions logical();

  /**
   * Access to capability related conditions.
   *
   * @return capability related conditions factory
   */
  CapabilityConditions capabilities();

  /**
   * Access to nexus specific conditions.
   *
   * @return nexus specific conditions factory
   */
  NexusConditions nexus();

  /**
   * Access to crypto specific conditions.
   *
   * @since 2.7
   */
  CryptoConditions crypto();

  /**
   * Creates a new condition that is always satisfied for the specified reason.
   * 
   * @since 3.1
   */
  Condition always(String reason);

  /**
   * Creates a new condition that is never satisfied for the specified reason.
   * 
   * @since 3.1
   */
  Condition never(String reason);
}
