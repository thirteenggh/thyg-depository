package org.sonatype.nexus.pax.exam.internal;

import java.util.function.Supplier;

import org.ops4j.pax.exam.ProbeInvoker;

/**
 * Delegates to the supplied {@link ProbeInvoker}.
 * 
 * @since 3.10
 */
public class DelegatingProbeInvoker
    implements ProbeInvoker
{
  private final Supplier<ProbeInvoker> supplier;

  private volatile ProbeInvoker delegate;

  public DelegatingProbeInvoker(final Supplier<ProbeInvoker> supplier) {
    this.supplier = supplier;
  }

  @Override
  public void call(final Object... args) {
    waitForDelegate().call(args);
  }

  private ProbeInvoker waitForDelegate() {
    if (delegate == null) {
      synchronized (this) {
        if (delegate == null) {
          delegate = supplier.get();
        }
      }
    }
    return delegate;
  }
}
