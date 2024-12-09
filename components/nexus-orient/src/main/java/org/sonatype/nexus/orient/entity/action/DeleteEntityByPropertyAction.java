package org.sonatype.nexus.orient.entity.action;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.orient.entity.EntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Delete entity based on one or more properties.
 *
 * @since 3.0
 */
public class DeleteEntityByPropertyAction
    extends ComponentSupport
{
  private final String query;

  public DeleteEntityByPropertyAction(final EntityAdapter<?> adapter, final String... properties) {
    checkNotNull(adapter);
    this.query = String.format("DELETE FROM %s WHERE %s", adapter.getTypeName(), QueryUtils.buildPredicate(properties));
  }

  public boolean execute(final ODatabaseDocumentTx db, final Object... values) {
    checkNotNull(db);
    checkArgument(values.length > 0);

    int records = db.command(new OCommandSQL(query))
        .execute(values);

    return records == 1;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "query='" + query + '\'' +
        '}';
  }
}
