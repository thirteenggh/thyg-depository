package org.sonatype.nexus.orient.entity.action;

import java.util.function.Predicate;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.IterableEntityAdapter;

import com.google.common.collect.Iterables;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Browse entities matching {@link Predicate}.
 *
 * @since 3.0
 */
public class BrowseEntitiesWithPredicateAction<T extends Entity>
    extends ComponentSupport
{
  private final IterableEntityAdapter<T> adapter;

  public BrowseEntitiesWithPredicateAction(final IterableEntityAdapter<T> adapter) {
    this.adapter = checkNotNull(adapter);
  }

  public Iterable<T> execute(final ODatabaseDocumentTx db, final Predicate<T> predicate) {
    checkNotNull(db);
    checkNotNull(predicate);

    return Iterables.filter(adapter.transform(adapter.browseDocuments(db)), predicate::test);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "type='" + adapter.getTypeName() + '\'' +
        '}';
  }
}
