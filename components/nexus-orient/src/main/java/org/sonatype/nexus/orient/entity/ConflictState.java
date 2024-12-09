package org.sonatype.nexus.orient.entity;

import java.util.function.Supplier;

/**
 * Potential conflict states - values are ordered from 'least' to 'most' amount of conflict.
 *
 * @since 3.14
 */
public enum ConflictState
{
  /**
   * No differences found so far; the original change could end up as a no-op.
   */
  IGNORE,
  /**
   * Found differences but no conflicts; the original change could still be applied.
   */
  ALLOW,
  /**
   * Found conflicts but they were deconflicted by merging further changes on top.
   */
  MERGE,
  /**
   * Found a conflict that cannot be deconflicted; the request must be denied.
   */
  DENY {
    @Override
    public ConflictState andThen(final Supplier<ConflictState> nextStep) {
      return DENY; // short-circuit any further deconflicting
    }
  };

  /**
   * Invokes the next step and returns whichever state is higher.
   */
  public ConflictState andThen(final Supplier<ConflictState> nextStep) {
    return max(this, nextStep.get());
  }

  public static ConflictState max(final ConflictState lhs, final ConflictState rhs) {
    return lhs.compareTo(rhs) >= 0 ? lhs : rhs;
  }
}
