package org.sonatype.nexus.repository.maven.internal.matcher;

import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPathParser;

/**
 * Matcher that matches any file, and sets {@link MavenPath} in context attributes.
 *
 * @since 3.0
 */
public class MavenPathMatcher
    extends MavenMatcherSupport
{
  public MavenPathMatcher(final MavenPathParser mavenPathParser) {
    super(mavenPathParser, (String path) -> Boolean.TRUE);
  }
}
