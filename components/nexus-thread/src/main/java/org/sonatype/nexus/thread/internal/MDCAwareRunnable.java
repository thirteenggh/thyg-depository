package org.sonatype.nexus.thread.internal;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Runnable that properly sets MDC context before invoking the delegate. The delegate will execute in a
 * managed thread with properly set MDC context. To be used with managed threads.
 *
 * @since 2.6
 */
public class MDCAwareRunnable
    implements Runnable
{
  private final Runnable delegate;

  private final Map<String, String> mdcContext;

  public MDCAwareRunnable(final Runnable delegate) {
    this.delegate = checkNotNull(delegate);
    this.mdcContext = MDCUtils.getCopyOfContextMap();
  }

  @Override
  public void run() {
    MDCUtils.setContextMap(mdcContext);
    delegate.run();
  }
}
