package org.sonatype.nexus.orient.freeze;

/**
 * Event for alerting when database state is changed.
 *
 * @since 3.2
 */
public class DatabaseFreezeChangeEvent
{
  private final boolean frozen;

  public DatabaseFreezeChangeEvent(final boolean frozen) {
    this.frozen = frozen;
  }

  public boolean isFrozen() {
    return frozen;
  }

  @Override
  public String toString() {
    return "DatabaseFreezeChangeEvent{" +
        "frozen=" + frozen +
        '}';
  }
}
