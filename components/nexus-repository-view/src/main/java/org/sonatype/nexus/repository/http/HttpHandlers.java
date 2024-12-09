package org.sonatype.nexus.repository.http;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

/**
 * Helper to create common HTTP {@link Handler} instances.
 *
 * @since 3.0
 */
public class HttpHandlers
{
  private HttpHandlers() {
    // empty
  }

  public static Handler notFound() {
    return new Handler()
    {
      @Nonnull
      @Override
      public Response handle(@Nonnull final Context context) throws Exception {
        return HttpResponses.notFound();
      }
    };
  }

  public static Handler badRequest() {
    return new Handler() {
      @Nonnull
      @Override
      public Response handle(@Nonnull final Context context) throws Exception {
        return HttpResponses.badRequest();
      }
    };
  }
}
