package org.sonatype.nexus.internal.capability.storage;

import java.util.Map;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.HasEntityId;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * {@link CapabilityStorageItem} data.
 *
 * @since 3.21
 */
public class CapabilityStorageItemData
    implements HasEntityId, CapabilityStorageItem
{
  // do not serialize EntityId, it can be generated on the fly
  @JsonIgnore
  private EntityId id;

  private int version;

  private String type;

  private boolean enabled;

  private String notes;

  private Map<String, String> properties;

  @Override
  public EntityId getId() {
    return id;
  }

  @Override
  public void setId(final EntityId id) {
    this.id = id;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(final int version) {
    this.version = version;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(final String type) {
    this.type = type;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String getNotes() {
    return notes;
  }

  @Override
  public void setNotes(final String notes) {
    this.notes = notes;
  }

  @Override
  public Map<String, String> getProperties() {
    return properties;
  }

  @Override
  public void setProperties(final Map<String, String> properties) {
    this.properties = properties;
  }
}
