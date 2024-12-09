package org.sonatype.nexus.repository.storage;

/**
 * Decorator interface for the {@link Component} class
 *
 * @since 3.8
 */
public interface ComponentDecorator
{
  Component decorate(Component component);
}
