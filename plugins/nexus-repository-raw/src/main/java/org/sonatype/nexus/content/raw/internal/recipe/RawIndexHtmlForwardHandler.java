package org.sonatype.nexus.content.raw.internal.recipe;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.handlers.IndexHtmlForwardHandler;

/**
 * Handler which will forward current request to {@code {request.path}/index.html} or {@code {request.path}/index.htm}.
 * If there are no index.html and index.htm files then just proceed to avoid 404 error
 *
 * @since 3.29
 */
@Named
@Singleton
public class RawIndexHtmlForwardHandler
    extends IndexHtmlForwardHandler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    Response response = super.handle(context);
    if (HttpStatus.NOT_FOUND == response.getStatus().getCode()) {
      return context.proceed();
    }

    return response;
  }
}
