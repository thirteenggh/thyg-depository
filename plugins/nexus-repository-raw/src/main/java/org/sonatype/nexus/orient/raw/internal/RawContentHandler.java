package org.sonatype.nexus.orient.raw.internal;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.orient.raw.RawContentFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;

import static com.google.common.base.Preconditions.checkState;
import static org.sonatype.nexus.repository.http.HttpMethods.DELETE;
import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD;
import static org.sonatype.nexus.repository.http.HttpMethods.PUT;

/**
 * Raw content hosted handler.
 *
 * @since 3.0
 */
@Named
@Singleton
public class RawContentHandler
    extends ComponentSupport
    implements Handler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    String name = contentName(context);
    String method = context.getRequest().getAction();

    Repository repository = context.getRepository();
    log.debug("{} repository '{}' content-name: {}", method, repository.getName(), name);

    RawContentFacet storage = repository.facet(RawContentFacet.class);

    switch (method) {
      case HEAD:
      case GET: {
        Content content = storage.get(name);
        if (content == null) {
          return HttpResponses.notFound(name);
        }
        return HttpResponses.ok(content);
      }

      case PUT: {
        Payload content = context.getRequest().getPayload();

        storage.put(name, content);
        return HttpResponses.created();
      }

      case DELETE: {
        boolean deleted = storage.delete(name);
        if (!deleted) {
          return HttpResponses.notFound(name);
        }
        return HttpResponses.noContent();
      }

      default:
        return HttpResponses.methodNotAllowed(method, GET, HEAD, PUT, DELETE);
    }
  }

  /**
   * Pull the parsed content name/path out of the context.
   */
  @Nonnull
  private String contentName(final Context context) {
    TokenMatcher.State state = context.getAttributes().require(TokenMatcher.State.class);
    String name = state.getTokens().get("name");
    checkState(name != null, "Missing token: name");

    return name;
  }
}
