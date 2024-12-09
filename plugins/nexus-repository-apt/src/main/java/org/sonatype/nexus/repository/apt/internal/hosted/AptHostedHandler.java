package org.sonatype.nexus.repository.apt.internal.hosted;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.apt.AptFacet;
import org.sonatype.nexus.repository.apt.internal.snapshot.AptSnapshotHandler;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;

import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD;
import static org.sonatype.nexus.repository.http.HttpMethods.POST;

/**
 * @since 3.17
 */
@Named
@Singleton
public class AptHostedHandler
    extends ComponentSupport
    implements Handler
{
  @Override
  public Response handle(final Context context) throws Exception {
    String path = assetPath(context);
    String method = context.getRequest().getAction();

    AptFacet aptFacet = context.getRepository().facet(AptFacet.class);
    AptHostedFacet hostedFacet = context.getRepository().facet(AptHostedFacet.class);

    switch (method) {
      case GET:
      case HEAD:
        return doGet(path, aptFacet);
      case POST:
        return doPost(context, path, method, hostedFacet);
      default:
        return HttpResponses.methodNotAllowed(method, GET, HEAD, POST);
    }
  }

  private Response doPost(final Context context,
                          final String path,
                          final String method,
                          final AptHostedFacet hostedFacet) throws IOException
  {
    if ("rebuild-indexes".equals(path)) {
      hostedFacet.rebuildIndexes();
      return HttpResponses.ok();
    }
    else if ("".equals(path)) {
      hostedFacet.ingestAsset(context.getRequest().getPayload());
      return HttpResponses.created();
    }
    else {
      return HttpResponses.methodNotAllowed(method, GET, HEAD);
    }
  }

  private Response doGet(final String path, final AptFacet aptFacet) throws IOException {
    Content content = aptFacet.get(path);
    if (content == null) {
      return HttpResponses.notFound(path);
    }
    return HttpResponses.ok(content);
  }

  private String assetPath(final Context context) {
    return context.getAttributes().require(AptSnapshotHandler.State.class).assetPath;
  }
}
