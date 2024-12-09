package org.sonatype.nexus.internal.security.anonymous.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.internal.security.anonymous.AnonymousConfigurationEvent;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;

/**
 * {@link AnonymousConfiguration} created event.
 *
 * @since 3.2
 */
public class OrientAnonymousConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements AnonymousConfigurationEvent
{
  public OrientAnonymousConfigurationCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public AnonymousConfiguration getAnonymousConfiguration() {
    return getEntity();
  }
}
