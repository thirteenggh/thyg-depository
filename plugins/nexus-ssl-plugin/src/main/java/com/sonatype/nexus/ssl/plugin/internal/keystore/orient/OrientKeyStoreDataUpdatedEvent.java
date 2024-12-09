package com.sonatype.nexus.ssl.plugin.internal.keystore.orient;

import com.sonatype.nexus.ssl.plugin.internal.keystore.KeyStoreDataEvent;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Emitted when a {@link OrientKeyStoreData} entity has been updated.
 *
 * @since 3.1
 */
public class OrientKeyStoreDataUpdatedEvent
    extends EntityUpdatedEvent
    implements KeyStoreDataEvent
{
  private final String keyStoreName;

  public OrientKeyStoreDataUpdatedEvent(final EntityMetadata metadata, final String keyStoreName) {
    super(metadata);
    this.keyStoreName = checkNotNull(keyStoreName);
  }

  @Override
  public String getKeyStoreName() {
    return keyStoreName;
  }
}
