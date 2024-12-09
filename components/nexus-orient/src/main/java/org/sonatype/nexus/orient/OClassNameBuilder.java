package org.sonatype.nexus.orient;

import java.util.Locale;

import javax.annotation.Nullable;

import com.orientechnologies.orient.core.metadata.schema.OClass;

import static com.google.common.base.Preconditions.checkState;

/**
 * Helper to build {@link OClass} names.
 *
 * @since 3.0
 */
public class OClassNameBuilder
{
  private static final String PREFIX_SEPARATOR = "_";

  @Nullable
  private String prefix;

  private String type;

  public OClassNameBuilder prefix(final String prefix) {
    this.prefix = prefix;
    return this;
  }

  public OClassNameBuilder type(final String type) {
    this.type = type;
    return this;
  }

  public String build() {
    // prefix is optional
    checkState(type != null, "Type required");

    StringBuilder buff = new StringBuilder();
    if (prefix != null) {
      buff.append(prefix);
      buff.append(PREFIX_SEPARATOR);
    }
    buff.append(type);

    // OClass names are always lower-case
    return buff.toString().toLowerCase(Locale.US);
  }
}
