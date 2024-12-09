package org.sonatype.nexus.internal.security.anonymous.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.internal.security.anonymous.AnonymousConfigurationEvent;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;

/**
 * {@link AnonymousConfiguration} deleted event.
 *
 * @since 3.2
 */
public class OrientAnonymousConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements AnonymousConfigurationEvent
{
  public OrientAnonymousConfigurationDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public AnonymousConfiguration getAnonymousConfiguration() {
    return getEntity();
  }
}
