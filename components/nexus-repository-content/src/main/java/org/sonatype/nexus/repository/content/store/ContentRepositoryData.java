package org.sonatype.nexus.repository.content.store;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.content.ContentRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link ContentRepository} data backed by the content data store.
 *
 * @since 3.20
 */
public class ContentRepositoryData
    extends AbstractRepositoryContent
    implements ContentRepository
{
  private EntityId configRepositoryId;

  // ContentRepository API

  @Override
  public EntityId configRepositoryId() {
    return configRepositoryId;
  }

  @Override
  public Integer contentRepositoryId() {
    return repositoryId;
  }

  // MyBatis setters + validation

  /**
   * Sets the config repository id.
   */
  public void setConfigRepositoryId(final EntityId configRepositoryId) {
    this.configRepositoryId = checkNotNull(configRepositoryId);
  }

  @Override
  public String toString() {
    return "ContentRepositoryData{" +
        "configRepositoryId=" + configRepositoryId +
        "} " + super.toString();
  }
}
