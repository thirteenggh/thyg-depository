package org.sonatype.nexus.repository.transaction;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.sonatype.nexus.repository.storage.MissingBlobException;
import org.sonatype.nexus.transaction.Operations;
import org.sonatype.nexus.transaction.Transactional;

import com.orientechnologies.common.concur.ONeedRetryException;
import com.orientechnologies.common.concur.lock.OModificationOperationProhibitedException;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates the method will access blobs and "touch" book-keeping attributes like last-downloaded time.
 *
 * Unlike {@link TransactionalStoreBlob} this doesn't retry if there's a conflict with another update.
 *
 * @since 3.2
 */
@Transactional(retryOn = MissingBlobException.class, swallow = { ONeedRetryException.class,
    OModificationOperationProhibitedException.class })
@Target(METHOD)
@Retention(RUNTIME)
public @interface TransactionalTouchBlob
{
  /**
   * Helper to apply this transactional behaviour to lambdas.
   */
  Operations<RuntimeException, ?> operation = Transactional.operation.stereotype(TransactionalTouchBlob.class);
}
