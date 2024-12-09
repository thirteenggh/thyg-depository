package org.sonatype.nexus.testsuite.testsupport.utility;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Iterates through a List cyclically, without throwing ConcurrentModificationException.
 */
public class CyclingIterator<T>
    implements Iterator<T>
{
  private final List<T> list;

  private int index = 0;

  public CyclingIterator(final List<T> list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return !list.isEmpty();
  }

  @Override
  public synchronized T next() {
    checkState(!list.isEmpty());
    if (index >= list.size()) {
      index = 0;
    }
    return list.get(index++);
  }

  @Override
  public void remove() {
    // no-op
  }
}
