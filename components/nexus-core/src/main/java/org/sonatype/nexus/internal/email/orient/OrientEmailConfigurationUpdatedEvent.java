package org.sonatype.nexus.internal.email.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.internal.email.EmailConfigurationEvent;

/**
 * Emitted when an {@link EmailConfiguration} entity has been updated.
 *
 * @since 3.2
 */
public class OrientEmailConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements EmailConfigurationEvent
{
  public OrientEmailConfigurationUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public EmailConfiguration getEmailConfiguration() {
    return getEntity();
  }
}
