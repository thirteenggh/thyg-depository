package org.sonatype.nexus.orient.entity.action;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.orient.entity.EntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Delete all entities.
 *
 * @since 3.0
 */
public class DeleteEntitiesAction
    extends ComponentSupport
{
  private final String query;

  public DeleteEntitiesAction(final EntityAdapter<?> adapter) {
    checkNotNull(adapter);
    this.query = String.format("DELETE FROM %s", adapter.getTypeName());
  }

  public void execute(final ODatabaseDocumentTx db) {
    checkNotNull(db);
    db.command(new OCommandSQL(query)).execute();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "query='" + query + '\'' +
        '}';
  }
}
