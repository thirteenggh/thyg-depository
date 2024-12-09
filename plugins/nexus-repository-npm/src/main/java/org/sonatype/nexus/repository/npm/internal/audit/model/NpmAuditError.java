package org.sonatype.nexus.repository.npm.internal.audit.model;

/**
 * Enum of error codes and error messages for npm audit
 *
 * @since 3.24
 */
public enum NpmAuditError
{
  ABSENT_PARSING_FILE("Neither npm-shrinkwrap.json nor package-lock.json found"),
  PARSING_ISSUE("Can't parse npm-shrinkwrap.json or package-lock.json"),
  COMPONENT_NOT_FOUND("Component wasn't found in package-lock.json"),
  SERVER_INTERNAL_ERROR("Server error"),
  TIMEOUT_ERROR("Waiting for results too long"),
  INTERRUPT_ERROR("Interrupt error");

  private final String message;

  NpmAuditError(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
