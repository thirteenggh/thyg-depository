package org.sonatype.nexus.orient;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

/**
 * Supports declaring temporary overrides to the replication mode.
 *
 * @since 3.15
 */
public class ReplicationModeOverrides
{
  /**
   * Optional property to force replication to always wait for results, or never wait for results.
   */
  private static final Optional<Boolean> FORCE_WAIT_FOR_RESULTS = parseWaitForResultsFlag(
      System.getProperty("nexus.replication.waitForResults"));

  private static final ThreadLocal<Boolean> waitForResults = new ThreadLocal<>();

  private ReplicationModeOverrides() {
    // static utility class
  }

  /**
   * Should we wait for results of the current replication?
   */
  public static Optional<Boolean> shouldWaitForReplicationResults() {
    return ofNullable(FORCE_WAIT_FOR_RESULTS.orElseGet(waitForResults::get));
  }

  /**
   * Declare we don't want to wait for results of the current replication.
   */
  public static void dontWaitForReplicationResults() {
    waitForResults.set(FALSE);
  }

  /**
   * Clear any temporary overrides.
   */
  public static void clearReplicationModeOverrides() {
    waitForResults.remove();
  }

  private static Optional<Boolean> parseWaitForResultsFlag(final String flag) {
    if ("TRUE".equalsIgnoreCase(flag)) {
      return of(TRUE);
    }
    else if ("FALSE".equalsIgnoreCase(flag)) {
      return of(FALSE);
    }
    else {
      return empty();
    }
  }
}
