package org.sonatype.nexus.transaction;

/**
 * Represents some kind of storage with transactional behaviour.
 *
 * @since 3.19
 */
public interface TransactionalStore<S extends TransactionalSession<?>>
{
  /**
   * Opens a new {@link TransactionalSession}.
   */
  S openSession();
}
