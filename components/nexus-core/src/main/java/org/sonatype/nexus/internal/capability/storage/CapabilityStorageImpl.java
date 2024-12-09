package org.sonatype.nexus.internal.capability.storage;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityIdentity;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityUUID;
import org.sonatype.nexus.common.entity.HasEntityId;
import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.transaction.Transactional;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.collect.Streams.stream;
import static java.util.UUID.fromString;
import static java.util.function.Function.identity;

/**
 * MyBatis {@link CapabilityStorage} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class CapabilityStorageImpl
    extends ConfigStoreSupport<CapabilityStorageItemDAO>
    implements CapabilityStorage
{
  @Inject
  public CapabilityStorageImpl(final DataSessionSupplier sessionSupplier) {
    super(sessionSupplier);
  }

  @Override
  public CapabilityStorageItem newStorageItem(final int version,
                                              final String type,
                                              final boolean enabled,
                                              final String notes,
                                              final Map<String, String> properties)
  {
    CapabilityStorageItem item = new CapabilityStorageItemData();
    item.setVersion(version);
    item.setType(type);
    item.setEnabled(enabled);
    item.setNotes(notes);
    item.setProperties(properties);
    return item;
  }

  @Transactional
  @Override
  public CapabilityIdentity add(final CapabilityStorageItem item) {
    dao().create((CapabilityStorageItemData) item);
    return capabilityIdentity(item);
  }

  @Transactional
  @Override
  public boolean update(final CapabilityIdentity id, final CapabilityStorageItem item) {
    ((HasEntityId)item).setId(entityId(id));
    return dao().update((CapabilityStorageItemData) item);
  }

  @Transactional
  @Override
  public boolean remove(final CapabilityIdentity id) {
    return dao().delete(entityId(id));
  }

  @Transactional
  @Override
  public Map<CapabilityIdentity, CapabilityStorageItem> getAll() {
    return stream(dao().browse()).collect(toImmutableMap(this::capabilityIdentity, identity()));
  }

  private CapabilityIdentity capabilityIdentity(final CapabilityStorageItem item) {
    return new CapabilityIdentity(((HasEntityId)item).getId().getValue());
  }

  private EntityId entityId(final CapabilityIdentity id) {
    return new EntityUUID(fromString(id.toString()));
  }
}
