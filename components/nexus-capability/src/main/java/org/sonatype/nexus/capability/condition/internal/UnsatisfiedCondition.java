package org.sonatype.nexus.capability.condition.internal;

import org.sonatype.nexus.capability.Condition;

/**
 * A condition that is never satisfied.
 *
 * @since capabilities 2.0
 */
public class UnsatisfiedCondition
    implements Condition
{

  private final String reason;

  public UnsatisfiedCondition(final String reason) {
    this.reason = reason;
  }

  @Override
  public boolean isSatisfied() {
    return false;
  }

  @Override
  public UnsatisfiedCondition bind() {
    // do nothing
    return this;
  }

  @Override
  public UnsatisfiedCondition release() {
    // do nothing
    return this;
  }

  @Override
  public String toString() {
    return reason;
  }

  @Override
  public String explainSatisfied() {
    return "Not " + reason;
  }

  @Override
  public String explainUnsatisfied() {
    return reason;
  }
}
