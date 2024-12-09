package org.sonatype.nexus.content.maven.internal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.maven.MavenPath.Coordinates;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.maven.internal.utils.MavenVariableResolverAdapterUtil;
import org.sonatype.nexus.repository.security.VariableResolverAdapterSupport;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.selector.VariableSourceBuilder;

import org.elasticsearch.search.lookup.SourceLookup;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maven2 implementation will expose the groupId/artifactId/version/extension/classifier attributes when available.
 *
 * @since 3.25
 */
@Named(Maven2Format.NAME)
public class MavenVariableResolverAdapter
    extends VariableResolverAdapterSupport
{
  private final MavenPathParser mavenPathParser;

  @Inject
  public MavenVariableResolverAdapter(@Named(Maven2Format.NAME) final MavenPathParser mavenPathParser) {
    this.mavenPathParser = checkNotNull(mavenPathParser);
  }

  @Override
  protected void addFromRequest(final VariableSourceBuilder builder, final Request request) {
    addMavenCoordinates(builder, request.getPath());
  }

  @Override
  protected void addFromSourceLookup(
      final VariableSourceBuilder builder,
      final SourceLookup sourceLookup,
      final Map<String, Object> asset)
  {
    addMavenCoordinates(builder, (String) asset.get(NAME));
  }

  /**
   * Adds the Maven coordinates extracted from the specified path, if available.
   */
  private void addMavenCoordinates(final VariableSourceBuilder builder, final String path) {
    checkNotNull(builder);
    checkNotNull(path);
    Coordinates coords = mavenPathParser.parsePath(path).getCoordinates();

    if (coords != null) {
      addCoordinates(builder, MavenVariableResolverAdapterUtil.createCoordinateMap(coords));
    }
  }
}
