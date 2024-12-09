package org.sonatype.nexus.orient.entity.action;

import java.util.ArrayList;
import java.util.List;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.orient.entity.EntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Action which returns specific property value for all entities.
 *
 * @since 3.0
 */
public class BrowsePropertyAction<T>
    extends ComponentSupport
{
  private final EntityAdapter<?> adapter;

  private final String property;

  public BrowsePropertyAction(final EntityAdapter<?> adapter, final String property) {
    this.adapter = checkNotNull(adapter);
    this.property = checkNotNull(property);
  }

  public List<T> execute(final ODatabaseDocumentTx db) {
    checkNotNull(db);
    List<T> result = new ArrayList<>();
    for (ODocument doc : adapter.browseDocuments(db)) {
      result.add(doc.field(property));
    }
    return result;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "property='" + property + '\'' +
        '}';
  }
}
