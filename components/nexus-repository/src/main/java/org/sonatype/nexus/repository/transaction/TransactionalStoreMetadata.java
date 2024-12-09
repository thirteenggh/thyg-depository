package org.sonatype.nexus.repository.transaction;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.sonatype.nexus.transaction.Operations;
import org.sonatype.nexus.transaction.Transactional;

import com.orientechnologies.common.concur.ONeedRetryException;
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the method will create or update metadata.
 *
 * @since 3.2
 */
@Transactional(retryOn = { ONeedRetryException.class, ORecordDuplicatedException.class })
@Target(METHOD)
@Retention(RUNTIME)
public @interface TransactionalStoreMetadata
{
  /**
   * Helper to apply this transactional behaviour to lambdas.
   */
  Operations<RuntimeException, ?> operation = Transactional.operation.stereotype(TransactionalStoreMetadata.class);
}
