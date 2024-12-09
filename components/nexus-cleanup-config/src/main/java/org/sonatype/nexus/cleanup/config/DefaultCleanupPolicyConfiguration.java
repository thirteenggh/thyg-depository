package org.sonatype.nexus.cleanup.config;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;

import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.IS_PRERELEASE_KEY;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.LAST_BLOB_UPDATED_KEY;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.LAST_DOWNLOADED_KEY;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.REGEX_KEY;

/**
 * Defines which default cleanup policy fields to display.
 *
 * @since 3.14
 */
@Named("default")
@Singleton
public class DefaultCleanupPolicyConfiguration
    implements CleanupPolicyConfiguration
{
  @Override
  public Map<String, Boolean> getConfiguration() {
    return ImmutableMap.of(LAST_BLOB_UPDATED_KEY, true,
        LAST_DOWNLOADED_KEY, true,
        IS_PRERELEASE_KEY, false,
        REGEX_KEY, false);
  }
}
