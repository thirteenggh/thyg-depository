package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Component deleted event.
 *
 * @since 3.0
 */
public class ComponentDeletedEvent
    extends EntityDeletedEvent
    implements ComponentEvent
{
  private final String repositoryName;

  public ComponentDeletedEvent(final EntityMetadata metadata, final String repositoryName) {
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
