package org.sonatype.nexus.repository.transaction;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.sonatype.nexus.repository.storage.MissingBlobException;
import org.sonatype.nexus.transaction.Operations;
import org.sonatype.nexus.transaction.Transactional;

import com.orientechnologies.common.concur.ONeedRetryException;
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the method will create or replace blobs.
 *
 * @since 3.2
 */
@Transactional(retryOn = { ONeedRetryException.class, ORecordDuplicatedException.class, MissingBlobException.class })
@Target(METHOD)
@Retention(RUNTIME)
public @interface TransactionalStoreBlob
{
  /**
   * Helper to apply this transactional behaviour to lambdas.
   */
  Operations<RuntimeException, ?> operation = Transactional.operation.stereotype(TransactionalStoreBlob.class);
}
