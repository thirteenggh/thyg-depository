package org.sonatype.nexus.orient.entity;

import javax.annotation.Nonnull;

import org.sonatype.nexus.common.entity.EntityVersion;

import com.orientechnologies.orient.core.record.ORecordVersionHelper;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Attached {@link EntityVersion}.
 *
 * @since 3.0
 */
public class AttachedEntityVersion
    implements EntityVersion
{
  private final EntityAdapter<?> owner;

  private final int version;

  /**
   * Cached encoded value of version.
   */
  private volatile String encoded;

  public AttachedEntityVersion(final EntityAdapter<?> owner, final int version) {
    this.owner = checkNotNull(owner);
    this.version = version;
  }

  public int getVersion() {
    return version;
  }

  @Override
  @Nonnull
  public String getValue() {
    if (encoded == null) {
      checkState(!ORecordVersionHelper.isTemporary(version), "attempted use of temporary/uncommitted document version");
      encoded = Integer.toString(version);
    }
    return encoded;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    else if (o instanceof AttachedEntityVersion) {
      AttachedEntityVersion that = (AttachedEntityVersion)o;
      return version == that.version;
    }
    else if (o instanceof EntityVersion) {
      EntityVersion that = (EntityVersion)o;
      return getValue().equals(that.getValue());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '{' +
        owner.getSchemaType() + "->" +
        version +
        '}';
  }
}
