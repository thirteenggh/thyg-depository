package org.sonatype.nexus.repository.content.event.component;

import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.store.ContentStoreEvent;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.content.store.InternalIds.contentRepositoryId;

/**
 * Base {@link Component} event.
 *
 * @since 3.26
 */
public class ComponentEvent
    extends ContentStoreEvent
{
  private final Component component;

  protected ComponentEvent(final Component component) {
    super(contentRepositoryId(component));
    this.component = checkNotNull(component);
  }

  public Component getComponent() {
    return component;
  }

  @Override
  public String toString() {
    return "ComponentEvent{" +
        "component=" + component +
        "} " + super.toString();
  }
}
