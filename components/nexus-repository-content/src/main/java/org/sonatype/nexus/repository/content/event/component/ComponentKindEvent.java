package org.sonatype.nexus.repository.content.event.component;

import org.sonatype.nexus.repository.content.Component;

/**
 * Event sent whenever a {@link Component}'s kind is updated.
 *
 * @since 3.26
 */
public class ComponentKindEvent
    extends ComponentUpdatedEvent
{
  public ComponentKindEvent(final Component component) {
    super(component);
  }
}
