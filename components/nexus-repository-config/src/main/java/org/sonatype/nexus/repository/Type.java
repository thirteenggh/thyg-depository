package org.sonatype.nexus.repository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repository type symbol.
 *
 * @since 3.0
 */
public abstract class Type
{
  private final String value;

  public Type(final String value) {
    this.value = checkNotNull(value);
  }

  public String getValue() {
    return value;
  }

  public abstract Class<?> getValidationGroup();

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Type type = (Type) o;

    if (!value.equals(type.value)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return value;
  }

}
