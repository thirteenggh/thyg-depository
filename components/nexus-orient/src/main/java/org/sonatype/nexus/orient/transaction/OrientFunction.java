package org.sonatype.nexus.orient.transaction;

import java.util.function.Function;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * Like {@link Function} but with a specific throws clause.
 *
 * @since 3.2
 */
@FunctionalInterface
public interface OrientFunction<T, E extends Exception>
{
  T apply(ODatabaseDocumentTx db) throws E;
}
