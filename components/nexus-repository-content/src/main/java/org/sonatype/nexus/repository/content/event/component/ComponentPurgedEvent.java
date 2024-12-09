package org.sonatype.nexus.repository.content.event.component;

import java.util.Arrays;

import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.store.ContentStoreEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Event sent whenever a large number of {@link Component}s are purged along with their assets.
 *
 * @since 3.26
 */
public class ComponentPurgedEvent
    extends ContentStoreEvent
{
  private final int[] componentIds;

  public ComponentPurgedEvent(final int contentRepositoryId, final int[] componentIds) { // NOSONAR
    super(contentRepositoryId);
    this.componentIds = checkNotNull(componentIds);
  }

  public int[] getComponentIds() {
    return componentIds; // NOSONAR
  }

  @Override
  public String toString() {
    return "ComponentPurgedEvent{" +
        "componentIds=" + Arrays.toString(componentIds) +
        "} " + super.toString();
  }
}
