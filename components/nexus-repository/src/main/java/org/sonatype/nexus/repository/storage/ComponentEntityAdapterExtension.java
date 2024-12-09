package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.orient.entity.EntityAdapter.EventKind;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Extension point for the {@link ComponentEntityAdapter} to define/read/write additional fields to Orient on a {@link
 * Component}  instance
 *
 * @since 3.8
 */
public interface ComponentEntityAdapterExtension
{
  /**
   * @see ComponentEntityAdapter#defineType(ODatabaseDocumentTx, OClass)
   */
  void defineType(ODatabaseDocumentTx db, OClass type);

  /**
   * @see ComponentEntityAdapter#readFields(ODocument, Component)
   */
  void readFields(ODocument document, Component component);

  /**
   * @see ComponentEntityAdapter#writeFields(ODocument, Component)
   */
  void writeFields(ODocument document, Component component);

  /**
   * Override this to capture lazy/linked content for component events.
   *
   * @see ComponentEntityAdapter#newEvent(ODocument, EventKind)
   */
  default void prefetchFields(ODocument document) {/* no-op */}
}
