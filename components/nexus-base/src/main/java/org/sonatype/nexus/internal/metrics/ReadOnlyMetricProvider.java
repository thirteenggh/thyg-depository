package org.sonatype.nexus.internal.metrics;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FreezeService;

import com.codahale.metrics.Metric;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Metric} provider for the database readonly (frozen) status.
 *
 * @since 3.6
 */
@Named("readonly")
@Singleton
public class ReadOnlyMetricProvider
    implements Provider<Metric>
{
  private final Provider<FreezeService> freezeServiceProvider;

  @Inject
  public ReadOnlyMetricProvider(final Provider<FreezeService> freezeServiceProvider) {
    this.freezeServiceProvider = checkNotNull(freezeServiceProvider);
  }

  public Metric get() {
    return new ReadOnlyMetricSet(freezeServiceProvider);
  }
}
