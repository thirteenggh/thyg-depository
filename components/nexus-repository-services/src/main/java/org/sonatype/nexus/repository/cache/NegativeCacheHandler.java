package org.sonatype.nexus.repository.cache;

import java.util.Set;

import javax.annotation.Nonnull;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.http.HttpMethods;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.Status;

import com.google.common.collect.ImmutableSet;

/**
 * Handler that caches 404 responses.
 *
 * When context invocation returns 404, it caches the 404 status to avoid future invocations (if cached status is
 * present).
 *
 * @since 3.0
 */
public class NegativeCacheHandler
    extends ComponentSupport
    implements Handler
{
  private static final Set<String> NFC_CACHEABLE_ACTIONS = ImmutableSet.of(HttpMethods.GET, HttpMethods.HEAD);
  
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    String action = context.getRequest().getAction();
    if (!NFC_CACHEABLE_ACTIONS.contains(action)) {
      return context.proceed();
    }
    NegativeCacheFacet negativeCache = context.getRepository().facet(NegativeCacheFacet.class);
    NegativeCacheKey key = negativeCache.getCacheKey(context);

    Response response;
    Status status = negativeCache.get(key);
    if (status == null) {
      response = context.proceed();
      if (isNotFound(response)) {
        negativeCache.put(key, response.getStatus());
      }
      else if (response.getStatus().isSuccessful()) {
        negativeCache.invalidate(key);
      }
    }
    else {
      response = buildResponse(status, context);

      log.debug("Found {} in negative cache, returning {}", key, response);
    }
    return response;
  }
  
  protected Response buildResponse(final Status status, final Context context) {
    return new Response.Builder()
        .status(status)
        .build();  
  }
  
  private boolean isNotFound(final Response response) {
    return HttpStatus.NOT_FOUND == response.getStatus().getCode();
  }

}
