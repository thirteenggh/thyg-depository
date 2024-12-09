package org.sonatype.nexus.content.maven.internal.recipe;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.content.maven.MavenMetadataRebuildContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD;

/**
 * @since 3.26
 */
@Named
@Singleton
public class MavenMetadataRebuildHandler
    extends ComponentSupport
    implements Handler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception
  {
    String method = context.getRequest().getAction();
    Repository repository = context.getRepository();
    if ((GET.equals(method) || HEAD.equals(method)) && isNotProxy(repository)) {
      repository.facet(MavenMetadataRebuildContentFacet.class)
          .maybeRebuildMavenMetadata(context.getRequest().getPath(), false, true);
    }
    return context.proceed();
  }

  protected boolean isNotProxy(@Nonnull final Repository repository) {
    return !ProxyType.NAME.equals(repository.getType().getValue());
  }
}
