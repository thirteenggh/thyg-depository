package org.sonatype.nexus.repository.routing;

/**
 * @since 3.16
 */
public enum RoutingMode
{
  /**
   * Allow requests that match the rule(s)
   */
  ALLOW,

  /**
   * Block requests that match the rule(s)
   */
  BLOCK
}
