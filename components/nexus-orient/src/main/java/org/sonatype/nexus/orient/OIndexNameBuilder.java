package org.sonatype.nexus.orient;

import java.util.List;
import java.util.Locale;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.index.OIndex;

import static com.google.common.base.Preconditions.checkState;

/**
 * Helper to build {@link OIndex} names.
 *
 * @since 3.0
 */
public class OIndexNameBuilder
{
  public static final String TYPE_SEPARATOR = "_";

  public static final String PROPERTY_SEPARATOR = "_";

  public static final String CASE_INSENSITIVE = "_ci";

  public static final String SUFFIX = "_idx";

  private String type;

  private List<String> properties = Lists.newArrayList();

  private boolean caseInsensitive;

  public OIndexNameBuilder type(final String type) {
    this.type = type;
    return this;
  }

  public OIndexNameBuilder property(final String property) {
    properties.add(property);
    return this;
  }

  public OIndexNameBuilder caseInsensitive() {
    caseInsensitive = true;
    return this;
  }

  public String build() {
    // type is optional
    checkState(!properties.isEmpty(), "At least one property required");

    StringBuilder buff = new StringBuilder();
    if (type != null) {
      buff.append(type);
      buff.append(TYPE_SEPARATOR);
    }
    Joiner.on(PROPERTY_SEPARATOR).appendTo(buff, properties);
    if (caseInsensitive) {
      buff.append(CASE_INSENSITIVE);
    }
    buff.append(SUFFIX);

    // OIndex names are always lower-case
    return buff.toString().toLowerCase(Locale.US);
  }
}
