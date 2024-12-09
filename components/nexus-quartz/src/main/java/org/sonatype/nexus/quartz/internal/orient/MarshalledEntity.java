package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.AbstractEntity;

/**
 * Marshalled entity.
 *
 * @see MarshalledEntityAdapter
 * @since 3.0
 */
public abstract class MarshalledEntity<T>
    extends AbstractEntity
{
  private T value;

  public T getValue() {
    return value;
  }

  public void setValue(final T value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "value=" + value +
        '}';
  }
}
