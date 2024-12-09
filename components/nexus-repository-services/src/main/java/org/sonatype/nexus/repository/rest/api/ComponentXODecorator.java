package org.sonatype.nexus.repository.rest.api;

/**
 * Decorator interface for the {@link ComponentXO} class
 *
 * @since 3.8
 */
public interface ComponentXODecorator
{
  ComponentXO decorate(ComponentXO componentXO);
}
