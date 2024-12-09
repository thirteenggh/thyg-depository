package org.sonatype.nexus.orient.entity;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.sonatype.nexus.common.entity.DetachedEntityId;
import org.sonatype.nexus.common.entity.DetachedEntityMetadata;
import org.sonatype.nexus.common.entity.DetachedEntityVersion;
import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityVersion;

import com.google.common.base.Throwables;
import com.orientechnologies.orient.core.db.record.ORecordElement;
import com.orientechnologies.orient.core.record.impl.ODocument;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Attached {@link EntityMetadata}.
 *
 * @since 3.0
 */
public class AttachedEntityMetadata
    implements EntityMetadata
{
  private final EntityAdapter owner;

  private final ODocument document;

  private final EntityId id;

  private final EntityVersion version;

  public AttachedEntityMetadata(final EntityAdapter owner, final ODocument document) {
    this.owner = checkNotNull(owner);
    this.document = checkNotNull(document);
    this.id = new AttachedEntityId(owner, document.getIdentity());
    this.version = new AttachedEntityVersion(owner, document.getVersion());
  }

  public EntityAdapter getOwner() {
    return owner;
  }

  public ODocument getDocument() {
    return document;
  }

  @Override
  @Nonnull
  public EntityId getId() {
    return id;
  }

  @Override
  @Nonnull
  public EntityVersion getVersion() {
    return version;
  }

  /**
   * Returns detached entity metadata.
   */
  public DetachedEntityMetadata detach() {
    return new DetachedEntityMetadata(
        new DetachedEntityId(id.getValue()),
        new DetachedEntityVersion(version.getValue())
    );
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "schema=" + owner.getTypeName() +
        ", document=" + safeDocumentToString() +
        '}';
  }

  private String safeDocumentToString() {
    ORecordElement.STATUS oldStatus = document.getInternalStatus();
    try {
      // stop orient from loading missing fields as DB might not be set
      // and we only want to dump what was actually loaded at this point
      document.setInternalStatus(ORecordElement.STATUS.UNMARSHALLING);
      return document.toString();
    }
    catch (Exception e) { // NOSONAR
      return document.getIdentity().toString();
    }
    finally {
      document.setInternalStatus(oldStatus);
    }
  }

  @Override
  public <T> Optional<Class<T>> getEntityType() {
    return Optional.of(getOwner().getEntityType());
  }

  @Override
  public <T extends Entity> Optional<T> getEntity() {
    final Entity newEntity = getOwner().newEntity();
    newEntity.setEntityMetadata(this);
    try {
      getOwner().readFields(getDocument(), newEntity);
    }
    catch (Exception e) {
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }

    return Optional.of((T) newEntity);
  }
}
