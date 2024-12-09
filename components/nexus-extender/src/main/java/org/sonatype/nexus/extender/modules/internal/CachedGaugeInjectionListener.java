package org.sonatype.nexus.extender.modules.internal;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.spi.InjectionListener;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Registers the supplied cache gauage to the metrics registry
 * @since 3.26
 */
public class CachedGaugeInjectionListener<T>
    implements InjectionListener<T>
{
  private final MetricRegistry metricRegistry;

  private final String metricName;

  private final long timeout;

  private final TimeUnit timeUnit;

  private final Method method;

  public CachedGaugeInjectionListener(
      final MetricRegistry metricRegistry,
      final String metricName,
      final Method method,
      final long timeout,
      final TimeUnit timeUnit)
  {
    checkArgument(timeout > 0);
    this.metricRegistry = checkNotNull(metricRegistry);
    this.metricName = checkNotNull(metricName);
    this.timeout = timeout;
    this.timeUnit = checkNotNull(timeUnit);
    this.method = checkNotNull(method);
  }

  @Override
  public void afterInjection(final T t) {
    metricRegistry.register(metricName, new CachedGauge<Object>(timeout, timeUnit)
    {
      @Override
      protected Object loadValue() {
        try {
          return method.invoke(t);
        }
        catch (Exception e) {
          return new RuntimeException(e);
        }
      }
    });
  }
}
