package org.sonatype.nexus.orient.entity.action;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.IterableEntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Browse all entities.
 *
 * @since 3.0
 */
public class BrowseEntitiesAction<T extends Entity>
    extends ComponentSupport
{
  private final IterableEntityAdapter<T> adapter;

  public BrowseEntitiesAction(final IterableEntityAdapter<T> adapter) {
    this.adapter = checkNotNull(adapter);
  }

  public Iterable<T> execute(final ODatabaseDocumentTx db) {
    checkNotNull(db);

    return adapter.transform(adapter.browseDocuments(db));
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "type='" + adapter.getTypeName() + '\'' +
        '}';
  }
}
