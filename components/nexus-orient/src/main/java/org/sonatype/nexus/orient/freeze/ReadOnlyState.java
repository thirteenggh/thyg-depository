package org.sonatype.nexus.orient.freeze;

/**
 * Summary object representing read-only state.
 *
 * @since 3.6
 */
public interface ReadOnlyState
{
  /**
   * @return true if read-only is set
   */
  boolean isFrozen();

  /**
   * @return a summary of why read-only is in effect; will be an empty string if {@link #isFrozen()} returns false
   */
  String getSummaryReason();

  /**
   * @return true if read-only was initiated by a system task; false if initiated by a user (via REST or UI)
   */
  boolean isSystemInitiated();
}
