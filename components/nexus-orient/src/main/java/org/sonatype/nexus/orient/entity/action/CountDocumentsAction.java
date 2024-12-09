package org.sonatype.nexus.orient.entity.action;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.orient.entity.EntityAdapter;

import com.google.common.primitives.Ints;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Count all documents.
 *
 * @since 3.0
 */
public class CountDocumentsAction
    extends ComponentSupport
{
  private final String type;

  public CountDocumentsAction(final EntityAdapter<?> adapter) {
    checkNotNull(adapter);
    this.type = adapter.getTypeName();
  }

  public long execute(final ODatabaseDocumentTx db) {
    checkNotNull(db);
    return db.countClass(type);
  }

  /**
   * Same as {@link #execute(ODatabaseDocumentTx)} but safely casts result to integer.
   */
  public int executeI(final ODatabaseDocumentTx db) {
    long result = execute(db);
    return Ints.checkedCast(result);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "type='" + type + '\'' +
        '}';
  }
}
