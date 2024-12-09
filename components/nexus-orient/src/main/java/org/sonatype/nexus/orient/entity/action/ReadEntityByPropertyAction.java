package org.sonatype.nexus.orient.entity.action;

import java.util.List;

import javax.annotation.Nullable;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.EntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Read entity based on one or more properties.
 *
 * @since 3.0
 */
public class ReadEntityByPropertyAction<T extends Entity>
    extends ComponentSupport
{
  private final EntityAdapter<T> adapter;

  private final String query;

  public ReadEntityByPropertyAction(final EntityAdapter<T> adapter, final String... properties) {
    this.adapter = checkNotNull(adapter);
    this.query = String.format("SELECT FROM %s WHERE %s", adapter.getTypeName(), QueryUtils.buildPredicate(properties));
  }

  @Nullable
  public T execute(final ODatabaseDocumentTx db, final Object... values) {
    checkNotNull(db);
    checkArgument(values.length > 0);

    List<ODocument> results = db.command(new OSQLSynchQuery<>(query))
        .execute(values);

    if (results.isEmpty()) {
      return null;
    }
    return adapter.readEntity(results.get(0));
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "query='" + query + '\'' +
        '}';
  }
}
