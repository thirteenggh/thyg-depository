package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Component updated event.
 *
 * @since 3.0
 */
public class ComponentUpdatedEvent
    extends EntityUpdatedEvent
    implements ComponentEvent
{
  private final String repositoryName;

  public ComponentUpdatedEvent(final EntityMetadata metadata, final String repositoryName) {
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
