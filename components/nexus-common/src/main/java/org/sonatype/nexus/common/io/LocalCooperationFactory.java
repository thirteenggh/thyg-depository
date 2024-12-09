package org.sonatype.nexus.common.io;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Supplies local {@link Cooperation} points.
 *
 * @since 3.14
 */
@Named("local")
@Singleton
public class LocalCooperationFactory
    extends ScopedCooperationFactorySupport
{
  private final ConcurrentMap<String, CooperatingFuture<?>> localFutures = new ConcurrentHashMap<>();

  @Override
  @SuppressWarnings("unchecked")
  protected <T> CooperatingFuture<T> beginCooperation(final String scopedKey, final CooperatingFuture<T> future) {
    return (CooperatingFuture<T>) localFutures.putIfAbsent(scopedKey, future);
  }

  @Override
  protected <T> void endCooperation(final String scopedKey, final CooperatingFuture<T> future) {
    localFutures.remove(scopedKey, future);
  }

  @Override
  protected Stream<CooperatingFuture<?>> streamFutures(final String scope) {
    return localFutures.entrySet().stream()
        .filter(entry -> entry.getKey().startsWith(scope))
        .map(Entry::getValue);
  }
}
