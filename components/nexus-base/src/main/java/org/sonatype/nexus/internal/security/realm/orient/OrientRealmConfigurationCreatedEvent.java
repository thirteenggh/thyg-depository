package org.sonatype.nexus.internal.security.realm.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationEvent;

/**
 * Emitted when a {@link RealmConfiguration} entity has been created.
 *
 * @since 3.1
 */
public class OrientRealmConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements RealmConfigurationEvent
{
  public OrientRealmConfigurationCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  public RealmConfiguration getConfiguration() {
    return getEntity();
  }
}
