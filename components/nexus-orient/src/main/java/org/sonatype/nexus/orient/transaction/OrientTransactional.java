package org.sonatype.nexus.orient.transaction;

import javax.inject.Provider;

import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.transaction.Transactional;

import com.orientechnologies.common.concur.ONeedRetryException;

/**
 * Like {@link Transactional#operation} helper, but also provides access to underlying Orient database.
 *
 * @since 3.2
 */
public interface OrientTransactional
{
  /**
   * Helper to apply transactional behaviour to lambdas.
   */
  OrientOperations<RuntimeException, ?> operation = new OrientOperations<>();

  /**
   * Helper to apply transactional behaviour to lambdas; retries when OrientDB indicates it should.
   */
  OrientOperations<RuntimeException, ?> retryOperation = operation.retryOn(ONeedRetryException.class);

  /**
   * Builds transactional behaviour for the given database.
   */
  static OrientOperations<RuntimeException, ?> inTx(final Provider<DatabaseInstance> databaseInstance) {
    return operation.withDb(databaseInstance);
  }

  /**
   * Builds transactional behaviour for the given database; retries when OrientDB indicates it should.
   */
  static OrientOperations<RuntimeException, ?> inTxRetry(final Provider<DatabaseInstance> databaseInstance) {
    return retryOperation.withDb(databaseInstance);
  }
}
