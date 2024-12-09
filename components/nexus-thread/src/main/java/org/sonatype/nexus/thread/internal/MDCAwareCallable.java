package org.sonatype.nexus.thread.internal;

import java.util.Map;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Callable that properly sets MDC context before invoking the delegate. The delegate will execute in a
 * managed thread with properly set MDC context. To be used with managed threads.
 *
 * @since 2.6
 */
public class MDCAwareCallable<T>
    implements Callable<T>
{
  private final Callable<T> delegate;

  private final Map<String, String> mdcContext;

  public MDCAwareCallable(final Callable<T> delegate) {
    this.delegate = checkNotNull(delegate);
    this.mdcContext = MDCUtils.getCopyOfContextMap();
  }

  @Override
  public T call() throws Exception {
    MDCUtils.setContextMap(mdcContext);
    return delegate.call();
  }
}
