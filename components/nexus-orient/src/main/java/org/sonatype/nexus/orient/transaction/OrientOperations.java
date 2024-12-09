package org.sonatype.nexus.orient.transaction;

import javax.annotation.Nullable;
import javax.inject.Provider;

import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.transaction.Operations;
import org.sonatype.nexus.transaction.Transaction;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.TransactionalStore;

import static org.sonatype.nexus.orient.transaction.OrientTransaction.currentDb;

/**
 * Orient specific fluent API for wrapping lambda operations with {@link Transactional} behaviour
 *
 * @see Operations example usage
 *
 * @since 3.2
 */
public class OrientOperations<E extends Exception, B extends OrientOperations<E, B>>
    extends Operations<E, B>
{
  /**
   * Assumes the lambda may throw the given checked exception.
   */
  @Override
  public <X extends Exception> OrientOperations<X, ?> throwing(final Class<X> exceptionType) {
    return (OrientOperations<X, ?>) super.throwing(exceptionType);
  }

  /**
   * Uses the provided database to acquire {@link Transaction}s.
   */
  public final B withDb(final Provider<DatabaseInstance> db) {
    return super.withDb(() -> new OrientTransaction(db.get().acquire()));
  }

  /**
   * Calls the given function with {@link Transactional} behaviour.
   */
  public final <T> T call(final OrientFunction<T, E> function) throws E {
    return super.call(() -> function.apply(currentDb()));
  }

  /**
   * Calls the given consumer with {@link Transactional} behaviour.
   */
  public final void run(final OrientConsumer<E> consumer) throws E {
    super.run(() -> consumer.accept(currentDb()));
  }

  /**
   * Default settings.
   */
  protected OrientOperations() {
    // use superclass defaults
  }

  /**
   * Custom settings.
   */
  protected OrientOperations(final Transactional spec,
                             @Nullable final Class<E> throwing,
                             @Nullable final TransactionalStore<?> store)
  {
    super(spec, throwing, store);
  }

  /**
   * Copies the given settings into a new fluent step.
   */
  @Override
  protected <X extends Exception> Operations<X, ?> copy(final Transactional spec,
                                                        @Nullable final Class<X> throwing,
                                                        @Nullable final TransactionalStore<?> store)
  {
    return new OrientOperations<>(spec, throwing, store);
  }
}
