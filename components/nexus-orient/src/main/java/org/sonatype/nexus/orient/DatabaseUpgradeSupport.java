package org.sonatype.nexus.orient;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Provider;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.upgrade.Upgrade;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;

/**
 * Support for upgrades of OrientDB databases.
 * 
 * @since 3.3
 */
public abstract class DatabaseUpgradeSupport
    extends ComponentSupport
    implements Upgrade
{
  /**
   * Returns {@code true} if the given schema class exists, otherwise {@code false}.
   */
  protected static boolean hasSchemaClass(final Provider<DatabaseInstance> databaseInstance, final String className) {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      return db.getMetadata().getSchema().existsClass(className);
    }
  }

  /**
   * Runs a {@link BiConsumer} of {@link ODatabaseDocumentTx} and {@link OClass}
   * if the specified {@code className} exists.
   */
  protected static void withDatabaseAndClass(final Provider<DatabaseInstance> databaseInstance, final String className,
                                             final BiConsumer<ODatabaseDocumentTx, OClass> consumer)
  {
    withDatabase(databaseInstance, db -> {
      if (db.getMetadata().getSchema().existsClass(className)) {
        consumer.accept(db, db.getMetadata().getSchema().getClass(className));
      }
    });
  }

  /**
   * Runs a {@link Consumer} with a {@link ODatabaseDocumentTx}
   */
  protected static void withDatabase(final Provider<DatabaseInstance> databaseInstance,
                                     final Consumer<ODatabaseDocumentTx> consumer)
  {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      consumer.accept(db);
    }
  }
}
