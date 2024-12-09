package org.sonatype.nexus.orient.entity.action;

import java.util.Collection;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.IterableEntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.joining;

/**
 * Browse entities based on one or more properties.
 *
 * @since 3.8
 */
public class BrowseEntitiesByPropertyInSetAction<T extends Entity>
    extends ComponentSupport
{
  private final IterableEntityAdapter<T> adapter;

  private final String query;

  public BrowseEntitiesByPropertyInSetAction(final IterableEntityAdapter<T> adapter, final String property) {
    this.adapter = checkNotNull(adapter);
    this.query = String.format("SELECT * FROM %s WHERE %s IN [ %%s ]", adapter.getTypeName(), checkNotNull(property));
  }

  public Iterable<T> execute(final ODatabaseDocumentTx db, final Collection<? extends Object> values) {
    checkNotNull(db);
    checkArgument(values != null && values.size() > 0);

    String valueFields = values.stream().map(value -> "?").collect(joining(","));
    String adjustedQuery = String.format(query, valueFields);

    Iterable<ODocument> results = db.command(new OSQLSynchQuery<>(adjustedQuery)).execute(values.toArray());

    return adapter.transform(results);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "query='" + query + '\'' +
        '}';
  }
}
