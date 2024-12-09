package org.sonatype.nexus.repository.storage;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Asset created event.
 *
 * @since 3.0
 */
public class AssetCreatedEvent
    extends EntityCreatedEvent
    implements AssetEvent
{
  private final String repositoryName;

  private final EntityId componentId;

  public AssetCreatedEvent(final EntityMetadata metadata, final String repositoryName,
      @Nullable final EntityId componentId)
  {
    super(metadata);
    this.repositoryName = checkNotNull(repositoryName);
    this.componentId = componentId;
  }

  public String getRepositoryName() {
    return repositoryName;
  }

  @Nullable
  public EntityId getComponentId() {
    return componentId;
  }

  public EntityId getAssetId() {
    return getId();
  }

  public Asset getAsset() {
    return getEntity();
  }
}
