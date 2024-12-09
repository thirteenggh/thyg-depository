package org.sonatype.nexus.repository.config.internal.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationDeletedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repository {@link Configuration} deleted event.
 *
 * @since 3.1
 */
public class OrientConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements ConfigurationDeletedEvent
{
  private final String repositoryName;

  public OrientConfigurationDeletedEvent(final EntityMetadata metadata, final String repositoryName) {
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
