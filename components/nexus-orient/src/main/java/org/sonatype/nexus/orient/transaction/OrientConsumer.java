package org.sonatype.nexus.orient.transaction;

import java.util.function.Consumer;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * Like {@link Consumer} but with a specific throws clause.
 *
 * @since 3.2
 */
@FunctionalInterface
public interface OrientConsumer<E extends Exception>
{
  void accept(ODatabaseDocumentTx db) throws E;
}
