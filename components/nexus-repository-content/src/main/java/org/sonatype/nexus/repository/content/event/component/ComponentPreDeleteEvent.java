package org.sonatype.nexus.repository.content.event.component;

import org.sonatype.nexus.repository.content.Component;

/**
 * Event sent just before a {@link Component} is deleted.
 *
 * @since 3.27
 */
public class ComponentPreDeleteEvent
    extends ComponentEvent
{
  public ComponentPreDeleteEvent(final Component component) {
    super(component);
  }
}
