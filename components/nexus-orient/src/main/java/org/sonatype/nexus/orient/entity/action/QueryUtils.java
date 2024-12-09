package org.sonatype.nexus.orient.entity.action;

import java.util.Set;
import java.util.StringJoiner;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

/**
 * Private utility to share common logic across actions.
 * 
 * @since 3.1
 */
class QueryUtils
{
  private QueryUtils() {
    // hidden
  }

  public static String buildPredicate(String... properties) {
    checkArgument(properties.length > 0, "At least one property is required");
    StringBuilder builder = new StringBuilder(128);
    for (String property : properties) {
      if (builder.length() > 0) {
        builder.append(" AND ");
      }
      builder.append(property).append(" = ?");
    }
    return builder.toString();
  }

  /**
   * Build an Orient 'IN' style query based on the set of values
   * @since 3.8
   */
  public static String buildIn(final Set<?> values) {
    checkArgument(!values.isEmpty(), "At least one value is required");
    StringJoiner joiner = new StringJoiner(",");
    for (Object value : values) {
      joiner.add(format("'%s'", value.toString()));
    }
    return format("[%s]", joiner.toString());
  }
}
