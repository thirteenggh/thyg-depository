package org.sonatype.nexus.internal.security.realm.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationEvent;

/**
 * Emitted when a {@link RealmConfiguration} entity has been updated.
 *
 * @since 3.1
 */
public class OrientRealmConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements RealmConfigurationEvent
{
  public OrientRealmConfigurationUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  public RealmConfiguration getConfiguration() {
    return getEntity();
  }
}
