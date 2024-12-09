package org.sonatype.nexus.orient.entity;

import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.common.entity.EntityHelper;
import org.sonatype.nexus.common.entity.EntityMetadata;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Attached {@link Entity} helpers.
 *
 * @since 3.0
 */
public class AttachedEntityHelper
{
  private AttachedEntityHelper() {
    // empty
  }

  /**
   * Returns attached metadata of entity.
   */
  public static AttachedEntityMetadata metadata(final Entity entity) {
    EntityMetadata metadata = EntityHelper.metadata(entity);
    checkState(metadata instanceof AttachedEntityMetadata, "Entity not attached");
    return (AttachedEntityMetadata) metadata;
  }

  /**
   * Check if given entity is attached.
   */
  public static boolean isAttached(final Entity entity) {
    return EntityHelper.metadata(entity) instanceof AttachedEntityMetadata;
  }

  /**
   * Returns attached document of entity.
   */
  public static ODocument document(final Entity entity) {
    checkNotNull(entity);
    return metadata(entity).getDocument();
  }

  /**
   * Returns attached record-id of entity.
   */
  public static ORID id(final Entity entity) {
    return document(entity).getIdentity();
  }

  /**
   * Returns attached record-version of entity.
   */
  public static int version(final Entity entity) {
    return document(entity).getVersion();
  }
}
