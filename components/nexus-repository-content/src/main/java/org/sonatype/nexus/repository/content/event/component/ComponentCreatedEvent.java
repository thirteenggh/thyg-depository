package org.sonatype.nexus.repository.content.event.component;

import org.sonatype.nexus.repository.content.Component;

/**
 * Event sent whenever a {@link Component} is created.
 *
 * @since 3.26
 */
public class ComponentCreatedEvent
    extends ComponentEvent
{
  public ComponentCreatedEvent(final Component component) {
    super(component);
  }
}
