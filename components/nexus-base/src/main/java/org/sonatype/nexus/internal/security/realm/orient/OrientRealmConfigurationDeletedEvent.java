package org.sonatype.nexus.internal.security.realm.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationEvent;

/**
 * Emitted when a {@link RealmConfiguration} entity has been deleted.
 *
 * @since 3.1
 */
public class OrientRealmConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements RealmConfigurationEvent
{
  public OrientRealmConfigurationDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  public RealmConfiguration getConfiguration() {
    return getEntity();
  }
}
