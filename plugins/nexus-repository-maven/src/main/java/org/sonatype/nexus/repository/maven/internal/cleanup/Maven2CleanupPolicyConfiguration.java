package org.sonatype.nexus.repository.maven.internal.cleanup;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.cleanup.config.CleanupPolicyConfiguration;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;

import com.google.common.collect.ImmutableMap;

import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.IS_PRERELEASE_KEY;
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.LAST_BLOB_UPDATED_KEY;
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.LAST_DOWNLOADED_KEY;
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.REGEX_KEY;

/**
 * Defines which cleanup policy fields to display for maven.
 *
 * @since 3.14
 */
@Named(Maven2Format.NAME)
@Singleton
public class Maven2CleanupPolicyConfiguration
    implements CleanupPolicyConfiguration
{
  @Override
  public Map<String, Boolean> getConfiguration() {
    return ImmutableMap.of(LAST_BLOB_UPDATED_KEY, true,
        LAST_DOWNLOADED_KEY, true,
        IS_PRERELEASE_KEY, true,
        REGEX_KEY, true);
  }
}
