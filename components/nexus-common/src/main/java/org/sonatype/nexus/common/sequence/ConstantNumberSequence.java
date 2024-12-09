package org.sonatype.nexus.common.sequence;

/**
 * A simple sequence that is a constant number sequence.
 */
public class ConstantNumberSequence
    implements NumberSequence
{
  private final long val;

  public ConstantNumberSequence(long val) {
    this.val = val;
  }

  public long next() {
    return peek();
  }

  public long prev() {
    return peek();
  }

  public long peek() {
    return val;
  }

  public void reset() {
    // nothing
  }
}
