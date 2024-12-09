package org.sonatype.nexus.common.app;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;

/**
 * Manages {@link ManagedLifecycle} components.
 *
 * @since 3.3
 */
public abstract class ManagedLifecycleManager
    extends ComponentSupport
{
  /**
   * Returns the current phase.
   */
  public abstract Phase getCurrentPhase();

  /**
   * Attempts to move to the target phase by starting (or stopping) components phase-by-phase. If any components have
   * appeared since the last request which belong to the current phase or earlier then they are automatically started
   * before the current phase is changed. Similarly components that have disappeared are stopped.
   */
  public abstract void to(final Phase targetPhase) throws Exception;

  /**
   * Attempts to bounce the given phase by moving the lifecycle just before it then back towards the current phase,
   * re-running all the phases in between. If the bounce phase is after the current phase then it simply moves the
   * lifecycle forwards like {@link #to(Phase)}.
   *
   * @since 3.16
   */
  public abstract void bounce(final Phase bouncePhase) throws Exception;

  /**
   * Are we in the process of shutting down? (ie. moving to the {@code OFF} phase)
   *
   * @since 3.16
   */
  public static boolean isShuttingDown() {
    return shuttingDown;
  }

  private static volatile boolean shuttingDown;

  protected ManagedLifecycleManager() {
    shuttingDown = false;
  }

  /**
   * Flag that we are in the process of shutting down.
   *
   * @since 3.16
   */
  protected void declareShutdown() {
    log.info("Shutting down");
    shuttingDown = true;
  }
}
