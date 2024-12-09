package org.sonatype.nexus.repository.config.internal.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationCreatedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repository {@link Configuration} created event.
 *
 * @since 3.1
 */
public class OrientConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements ConfigurationCreatedEvent
{
  private final String repositoryName;

  public OrientConfigurationCreatedEvent(final EntityMetadata metadata, final String repositoryName) {
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
