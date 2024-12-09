package org.sonatype.nexus.cleanup.config;

/**
 * Cleanup policy constants.
 *
 * @since 3.24
 */
public interface CleanupPolicyConstants
{
  String CLEANUP_ATTRIBUTES_KEY = "cleanup";

  String CLEANUP_NAME_KEY = "policyName";

  String IS_PRERELEASE_KEY = "isPrerelease";

  String LAST_BLOB_UPDATED_KEY = "lastBlobUpdated";

  String LAST_DOWNLOADED_KEY = "lastDownloaded";

  String REGEX_KEY = "regex";
}
