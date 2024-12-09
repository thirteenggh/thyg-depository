package org.sonatype.nexus.repository.search.query;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.15
 */
public class SearchFilter
{
  private final String property;

  private final String value;

  public SearchFilter(final String property, final String value) {
    this.property = checkNotNull(property);
    this.value = checkNotNull(value);
  }

  public String getProperty() {
    return property;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchFilter that = (SearchFilter) o;
    return Objects.equals(property, that.property) &&
        Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(property, value);
  }

  @Override
  public String toString() {
    return "SearchFilter{" +
        "property='" + property + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
