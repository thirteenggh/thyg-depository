package org.sonatype.nexus.repository.maven.internal.matcher;

import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.maven.internal.Constants;

/**
 * Matcher that matches for Maven repository metadata only, and sets {@link MavenPath} in context attributes.
 *
 * @since 3.0
 */
public class MavenRepositoryMetadataMatcher
    extends MavenMatcherSupport
{
  private static final String MAVEN_METADATA_REQ_PATH = "/" + Constants.METADATA_FILENAME;

  public MavenRepositoryMetadataMatcher(final MavenPathParser mavenPathParser) {
    super(mavenPathParser,
        withHashes((String path) -> path.endsWith(MAVEN_METADATA_REQ_PATH))
    );
  }
}
