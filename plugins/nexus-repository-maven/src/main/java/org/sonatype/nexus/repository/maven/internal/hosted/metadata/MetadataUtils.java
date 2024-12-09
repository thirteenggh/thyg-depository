package org.sonatype.nexus.repository.maven.internal.hosted.metadata;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.internal.Constants;

/**
 * Utility class containing shared general methods for Maven metadata.
 *
 * @since 3.25
 */
public final class MetadataUtils
{
  private MetadataUtils() {
    //no op
  }

  /**
   * Builds a Maven path for the specified metadata.
   */
  public static MavenPath metadataPath(
      final String groupId,
      @Nullable final String artifactId,
      @Nullable final String baseVersion)
  {
    final StringBuilder sb = new StringBuilder();
    sb.append(groupId.replace('.', '/'));
    if (artifactId != null) {
      sb.append("/").append(artifactId);
      if (baseVersion != null) {
        sb.append("/").append(baseVersion);
      }
    }
    sb.append("/").append(Constants.METADATA_FILENAME);
    return new MavenPath(sb.toString(), null);
  }
}
