package org.sonatype.nexus.internal.security.anonymous.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.internal.security.anonymous.AnonymousConfigurationEvent;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;

/**
 * {@link AnonymousConfiguration} updated event.
 *
 * @since 3.2
 */
public class OrientAnonymousConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements AnonymousConfigurationEvent
{
  public OrientAnonymousConfigurationUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public AnonymousConfiguration getAnonymousConfiguration() {
    return getEntity();
  }
}
