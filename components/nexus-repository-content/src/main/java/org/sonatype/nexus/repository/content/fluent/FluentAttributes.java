package org.sonatype.nexus.repository.content.fluent;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.content.AttributeOperation;

import static org.sonatype.nexus.repository.content.AttributeOperation.REMOVE;
import static org.sonatype.nexus.repository.content.AttributeOperation.SET;

/**
 * Fluent API for repository content attributes.
 *
 * @since 3.21
 */
public interface FluentAttributes<A extends FluentAttributes<A>>
{
  /**
   * Sets the given attribute, overwriting any existing value.
   */
  default A withAttribute(String key, Object value) {
    return attributes(SET, key, value);
  }

  /**
   * Removes the given attribute from the current attributes.
   */
  default A withoutAttribute(String key) {
    return attributes(REMOVE, key, null);
  }

  /**
   * Applies the given change to the current attributes.
   */
  A attributes(AttributeOperation change, String key, @Nullable Object value);

}
