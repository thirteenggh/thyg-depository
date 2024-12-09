package org.sonatype.nexus.scheduling;

/**
 * A service that periodically runs {@link Runnable} tasks.
 *
 * @since 3.0
 */
public interface PeriodicJobService
{
  /**
   * Starts the service if it is not already started. Each successful invocation of this method must eventually be
   * paired with a corresponding invocation of {@link #stopUsing()}.
   * 
   * @since 3.1
   */
  void startUsing() throws Exception;

  /**
   * Signals the caller no longer needs the service. The service is stopped if this invocation balances out all previous
   * invocations of {@link #startUsing()}.
   * 
   * @since 3.1
   */
  void stopUsing() throws Exception;

  /**
   * Add a job to be periodically executed.
   *
   * @return a {@link PeriodicJob} that must be closed when the job is no longer necessary.
   */
  PeriodicJob schedule(Runnable runnable, final int repeatPeriodSeconds);

  interface PeriodicJob
  {
    void cancel();
  }
}
