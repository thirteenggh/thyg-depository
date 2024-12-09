package org.sonatype.nexus.content.maven.internal.recipe;

import javax.annotation.Nonnull;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

/**
 * Group specific handler of Maven indexes: it serves up merged group index, if present.
 *
 * @since 3.26
 */
class MavenContentIndexGroupHandler
    extends ComponentSupport
    implements Handler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    MavenPath mavenPath = context.getAttributes().require(MavenPath.class);
    MavenContentFacet mavenContentFacet = context.getRepository().facet(MavenContentFacet.class);
    return mavenContentFacet.get(mavenPath)
        .map(HttpResponses::ok)
        .orElseGet(() -> HttpResponses.notFound(mavenPath.getPath()));
  }
}
