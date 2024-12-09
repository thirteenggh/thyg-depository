package org.sonatype.nexus.repository.storage;

import java.util.concurrent.Callable;

import com.google.common.base.Throwables;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

/**
 * Utility class for working with different databases in the same thread. If you can find a way to avoid using this,
 * you probably should.
 *
 * @since 3.1
 */
public final class DatabaseThreadUtils
{
  /**
   * Utility function for working around "ODatabaseException: Database instance is not set in current thread" issues.
   * The current database ThreadLocal is preserved and restored after calling the lambda.
   */
  public static <T> T withOtherDatabase(Callable<T> function) {
    final ODatabaseDocumentInternal db = ODatabaseRecordThreadLocal.INSTANCE.getIfDefined();
    try {
      return function.call();
    }
    catch (Exception e) {
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }
    finally {
      ODatabaseRecordThreadLocal.INSTANCE.set(db);
    }
  }

  private DatabaseThreadUtils() {
    // empty
  }
}
