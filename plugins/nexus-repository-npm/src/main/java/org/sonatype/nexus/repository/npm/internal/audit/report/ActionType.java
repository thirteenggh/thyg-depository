package org.sonatype.nexus.repository.npm.internal.audit.report;

/**
 * Enum with types of decision for NPM component.
 *
 * @since 3.24
 */
public enum ActionType
{
  INSTALL("install"),
  UPDATE("update"),
  REVIEW("review");

  private final String type;

  ActionType(final String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
