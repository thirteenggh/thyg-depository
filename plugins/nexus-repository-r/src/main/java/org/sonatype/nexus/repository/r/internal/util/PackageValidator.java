package org.sonatype.nexus.repository.r.internal.util;

import org.sonatype.nexus.rest.ValidationErrorsException;

import static org.sonatype.nexus.repository.r.internal.util.RPathUtils.isValidArchiveExtension;
import static org.sonatype.nexus.repository.r.internal.util.RPathUtils.isValidRepoPath;

/**
 * Validator for repository packages
 *
 * @since 3.28
 */
public final class PackageValidator
{
  public static final String NOT_VALID_PATH_ERROR_MESSAGE =
      "Provided path is not valid and is expecting src/contrib or bin/<os>/contrib/<R_version>";

  public static final String NOT_VALID_EXTENSION_ERROR_MESSAGE = "Provided extension is not .zip, .tar.gz or .tgz";

  private PackageValidator() {
    // Empty
  }

  /**
   * Validates upload path, filename and extension.
   * <p>
   * Throws {@link org.sonatype.nexus.rest.ValidationErrorsException} if validation failed
   */
  public static void validateArchiveUploadPath(final String fullPath) {
    if (!isValidRepoPath(fullPath)) {
      throw new ValidationErrorsException(NOT_VALID_PATH_ERROR_MESSAGE);
    }
    if (!isValidArchiveExtension(fullPath)) {
      throw new ValidationErrorsException(NOT_VALID_EXTENSION_ERROR_MESSAGE);
    }
  }
}
