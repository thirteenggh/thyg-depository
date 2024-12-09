package org.sonatype.nexus.common.sequence;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Supplies local {@link AtomicSequence}s.
 *
 * @since 3.14
 */
@Named("local")
@Singleton
public class LocalAtomicSequenceFactory
    implements AtomicSequenceFactory
{
  @Override
  public AtomicSequence create(final String id, final LongSupplier initialValue) {
    AtomicLong sequence = new AtomicLong(initialValue.getAsLong());
    return () -> sequence.incrementAndGet();
  }
}
