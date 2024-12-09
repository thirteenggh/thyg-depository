package org.sonatype.nexus.repository.maven.internal.orient;

import javax.annotation.Nonnull;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.orient.maven.OrientMavenFacet;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

/**
 * Group specific handler of Maven indexes: it serves up merged group index, if present.
 *
 * @since 3.0
 */
public class OrientIndexGroupHandler
    extends ComponentSupport
    implements Handler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    MavenPath mavenPath = context.getAttributes().require(MavenPath.class);
    OrientMavenFacet mavenFacet = context.getRepository().facet(OrientMavenFacet.class);
    Content content = mavenFacet.get(mavenPath);
    if (content == null) {
      return HttpResponses.notFound(mavenPath.getPath());
    }
    return HttpResponses.ok(content);
  }
}
