package com.sonatype.nexus.ssl.plugin.internal.keystore.orient;

import com.sonatype.nexus.ssl.plugin.internal.keystore.KeyStoreDataEvent;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Emitted when a {@link OrientKeyStoreData} entity has been created.
 *
 * @since 3.1
 */
public class OrientKeyStoreDataCreatedEvent
    extends EntityCreatedEvent
    implements KeyStoreDataEvent
{
  private final String keyStoreName;

  public OrientKeyStoreDataCreatedEvent(final EntityMetadata metadata, final String keyStoreName) {
    super(metadata);
    this.keyStoreName = checkNotNull(keyStoreName);
  }

  @Override
  public String getKeyStoreName() {
    return keyStoreName;
  }
}
