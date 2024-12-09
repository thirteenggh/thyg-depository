package org.sonatype.nexus.orient.entity;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.action.SingletonActions;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Singleton record entity-adapter.
 *
 * @since 3.1
 */
public abstract class SingletonEntityAdapter<T extends Entity>
    extends EntityAdapter<T>
{
  protected final SingletonActions<T> singleton = new SingletonActions<>(this);

  public SingletonEntityAdapter(final String typeName) {
    super(typeName);
  }

  @Override
  protected int getMinimumClusters() {
    return 1; 
  }

  @Override
  public boolean sendEvents() {
    return true; // enable replication workaround for all singleton entities
  }

  //
  // Actions
  //

  /**
   * Get singleton entity or {@code null} if entity was not found.
   */
  @Nullable
  public T get(final ODatabaseDocumentTx db) {
    return singleton.get(db);
  }

  /**
   * Set singleton entity. Returns {@code true} if entity was replaced.
   */
  public boolean set(final ODatabaseDocumentTx db, final T entity) {
    return singleton.set(db, entity);
  }

  /**
   * Delete singleton entity. Returns {@code true} if entity was deleted.
   */
  public boolean delete(final ODatabaseDocumentTx db) {
    return singleton.delete(db);
  }

  /**
   * TEMP: workaround OrientDB 2.1 issue where in-TX dictionary updates are not replicated.
   */
  public void replicate(final ODocument document, final EventKind eventKind) {
    singleton.replicate(document, eventKind);
  }
}
