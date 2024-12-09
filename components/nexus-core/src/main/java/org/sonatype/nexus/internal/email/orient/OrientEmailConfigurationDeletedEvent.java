package org.sonatype.nexus.internal.email.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.internal.email.EmailConfigurationEvent;

/**
 * Emitted when an {@link EmailConfiguration} entity has been deleted.
 *
 * @since 3.2
 */
public class OrientEmailConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements EmailConfigurationEvent
{
  public OrientEmailConfigurationDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public EmailConfiguration getEmailConfiguration() {
    return getEntity();
  }
}
