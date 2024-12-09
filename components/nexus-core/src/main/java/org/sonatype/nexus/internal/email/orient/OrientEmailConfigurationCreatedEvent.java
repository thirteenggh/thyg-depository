package org.sonatype.nexus.internal.email.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.internal.email.EmailConfigurationEvent;

/**
 * Emitted when an {@link EmailConfiguration} entity has been created.
 *
 * @since 3.2
 */
public class OrientEmailConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements EmailConfigurationEvent
{
  public OrientEmailConfigurationCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public EmailConfiguration getEmailConfiguration() {
    return getEntity();
  }
}
