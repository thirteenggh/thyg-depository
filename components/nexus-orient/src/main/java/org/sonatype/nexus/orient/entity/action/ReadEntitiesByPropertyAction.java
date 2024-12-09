package org.sonatype.nexus.orient.entity.action;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.EntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.sonatype.nexus.orient.entity.action.QueryUtils.buildIn;

/**
 * Read entity based on a single property for an IN style query
 *
 * @since 3.8
 */
public class ReadEntitiesByPropertyAction<T extends Entity>
    extends ComponentSupport
{
  private final EntityAdapter<T> adapter;

  private final String query;

  public ReadEntitiesByPropertyAction(final EntityAdapter<T> adapter, final String property) {
    this.adapter = checkNotNull(adapter);
    this.query = format("SELECT FROM %s WHERE %s IN", adapter.getTypeName(), property);
  }

  @Nullable
  public List<T> execute(final ODatabaseDocumentTx db, final Set<?> values) {
    checkNotNull(db);
    checkArgument(!values.isEmpty());

    String q = query + buildIn(values);
    List<ODocument> results = db.command(new OSQLSynchQuery<>(q)).execute(values);

    return newArrayList(results.stream().map(adapter::readEntity).collect(toList()));
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "query='" + query + '\'' +
        '}';
  }
}
