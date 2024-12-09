package org.sonatype.nexus.repository.content.event.component;

import org.sonatype.nexus.repository.content.Component;

/**
 * Event sent whenever a {@link Component} is deleted.
 *
 * @since 3.26
 */
public class ComponentDeletedEvent
    extends ComponentEvent
{
  public ComponentDeletedEvent(final Component component) {
    super(component);
  }
}
