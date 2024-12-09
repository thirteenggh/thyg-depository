package org.sonatype.nexus.repository.npm.internal.audit.exceptions;

import static org.sonatype.nexus.repository.npm.internal.audit.model.NpmAuditError.PARSING_ISSUE;

/**
 * Thrown when package-lock parsing went wrong.
 *
 * @since 3.24
 */
public class PackageLockParsingException extends Exception
{
  public PackageLockParsingException() {
    super(PARSING_ISSUE.getMessage());
  }
}
