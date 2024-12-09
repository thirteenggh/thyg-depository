package org.sonatype.nexus.datastore.api;

import java.util.Map;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.transformEntries;
import static java.util.regex.Pattern.compile;

/**
 * {@link DataStore} configuration.
 *
 * @since 3.19
 */
public class DataStoreConfiguration
{
  private static final Predicate<String> SENSITIVE_KEYS =
      compile("(?i)(auth|cred|key|pass|secret|sign|token)").asPredicate();

  private String name;

  private String type;

  private String source;

  private Map<String, String> attributes;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = checkNotNull(name);
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = checkNotNull(type);
  }

  public String getSource() {
    return source;
  }

  public void setSource(final String source) {
    this.source = checkNotNull(source);
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public void setAttributes(final Map<String, String> attributes) {
    this.attributes = checkNotNull(attributes);
  }

  @Override
  public String toString() {
    return "{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", source='" + source + '\'' +
        ", attributes=" + redact(attributes) +
        '}';
  }

  /**
   * Redact output using a blacklist of potentially sensitive key patterns.
   */
  protected Map<String, String> redact(final Map<String, String> attributes) {
    return transformEntries(attributes, (k, v) -> SENSITIVE_KEYS.test(k) ? "**REDACTED**" : v);
  }
}
