package org.sonatype.nexus.repository.content.event.component;

import org.sonatype.nexus.repository.content.Component;

/**
 * Event sent whenever a {@link Component} is updated.
 *
 * @since 3.26
 */
public class ComponentUpdatedEvent
    extends ComponentEvent
{
  protected ComponentUpdatedEvent(final Component component) {
    super(component);
  }
}
