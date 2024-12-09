package org.sonatype.nexus.repository.apt.internal.gpg;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.apt.internal.snapshot.AptSnapshotHandler;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Response;


import static org.sonatype.nexus.repository.http.HttpMethods.GET;

/**
 * @since 3.17
 */
@Named
@Singleton
public class AptSigningHandler
    extends ComponentSupport
    implements Handler
{
  @Override
  public Response handle(final Context context) throws Exception {
    String path = assetPath(context);
    String method = context.getRequest().getAction();
    AptSigningFacet facet = context.getRepository().facet(AptSigningFacet.class);

    if ("repository-key.gpg".equals(path) && GET.equals(method)) {
      return HttpResponses.ok(facet.getPublicKey());
    }

    return context.proceed();
  }

  private String assetPath(final Context context) {
    return context.getAttributes().require(AptSnapshotHandler.State.class).assetPath;
  }
}
