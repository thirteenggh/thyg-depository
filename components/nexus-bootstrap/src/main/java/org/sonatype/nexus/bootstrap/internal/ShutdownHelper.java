package org.sonatype.nexus.bootstrap.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper to cope with different mechanisms to shutdown.
 *
 * @since 2.2
 */
public class ShutdownHelper
{
  private static final Logger log = LoggerFactory.getLogger(ShutdownHelper.class);

  private ShutdownHelper() {
    // empty
  }

  public interface ShutdownDelegate
  {
    void doExit(int code);

    void doHalt(int code);
  }

  public static class JavaShutdownDelegate
      implements ShutdownDelegate
  {
    @Override
    public void doExit(final int code) {
      // expected use of System.exit()
      System.exit(code);
    }

    @Override
    public void doHalt(final int code) {
      // expected use of Runtime.halt()
      Runtime.getRuntime().halt(code);
    }
  }

  public static class NoopShutdownDelegate
      implements ShutdownDelegate
  {
    @Override
    public void doExit(final int code) {
      log.warn("Ignoring exit({}) request", code);
    }

    @Override
    public void doHalt(final int code) {
      log.warn("Ignoring halt({}) request", code);
    }
  }

  public static final ShutdownDelegate JAVA = new JavaShutdownDelegate();

  public static final ShutdownDelegate NOOP = new NoopShutdownDelegate();

  private static ShutdownDelegate delegate = JAVA;

  public static ShutdownDelegate getDelegate() {
    if (delegate == null) {
      throw new IllegalStateException();
    }
    return delegate;
  }

  public static void setDelegate(final ShutdownDelegate delegate) {
    if (delegate == null) {
      throw new NullPointerException();
    }
    ShutdownHelper.delegate = delegate;
  }

  public static void exit(final int code) {
    getDelegate().doExit(code);
  }

  public static void halt(final int code) {
    getDelegate().doHalt(code);
  }
}
