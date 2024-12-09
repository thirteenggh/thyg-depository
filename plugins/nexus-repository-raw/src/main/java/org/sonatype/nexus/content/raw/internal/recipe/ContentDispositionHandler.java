package org.sonatype.nexus.content.raw.internal.recipe;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import static org.sonatype.nexus.repository.http.HttpMethods.GET;

/**
 * Handler to set Content-Disposition HTTP header
 *
 * @since 3.25
 */
@Named
@Singleton
public class ContentDispositionHandler
    implements Handler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    Response response = context.proceed();
    String action = context.getRequest().getAction();
    if (GET.equals(action)) {
      String contentDisposition = context.getRepository().getConfiguration().attributes("raw")
          .get("contentDisposition", String.class, ContentDisposition.INLINE.name());
      response.getHeaders().replace("Content-Disposition", ContentDisposition.valueOf(contentDisposition).getValue());
    }
    return response;
  }
}
