package org.sonatype.nexus.repository.config.internal.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationUpdatedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repository {@link Configuration} updated event.
 *
 * @since 3.1
 */
public class OrientConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements ConfigurationUpdatedEvent
{
  private final String repositoryName;

  public OrientConfigurationUpdatedEvent(final EntityMetadata metadata, final String repositoryName) {
    super(metadata);
    this.repositoryName = checkNotNull(repositoryName);
  }

  @Override
  public String getRepositoryName() {
    return repositoryName;
  }

  @Override
  public Configuration getConfiguration() {
    return getEntity();
  }
}
