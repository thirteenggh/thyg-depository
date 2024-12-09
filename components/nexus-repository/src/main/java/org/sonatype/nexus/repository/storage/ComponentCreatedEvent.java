package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Component created event.
 *
 * @since 3.0
 */
public class ComponentCreatedEvent
    extends EntityCreatedEvent
    implements ComponentEvent
{
  private final String repositoryName;

  public ComponentCreatedEvent(final EntityMetadata metadata, final String repositoryName) {
    super(metadata);
    this.repositoryName = checkNotNull(repositoryName);
  }

  public String getRepositoryName() {
    return repositoryName;
  }

  public EntityId getComponentId() {
    return getId();
  }

  public Component getComponent() {
    return getEntity();
  }
}
