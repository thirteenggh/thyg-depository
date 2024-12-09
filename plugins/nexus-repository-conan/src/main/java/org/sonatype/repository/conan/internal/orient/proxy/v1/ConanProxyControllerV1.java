package org.sonatype.repository.conan.internal.orient.proxy.v1;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.sonatype.nexus.repository.view.Router;
import org.sonatype.repository.conan.internal.orient.common.v1.ConanControllerV1;
import org.sonatype.repository.conan.internal.common.v1.ConanRoutes;
import org.sonatype.repository.conan.internal.proxy.v1.ConanProxyHandlers;

import static org.sonatype.repository.conan.internal.AssetKind.DIGEST;

/**
 * @since 3.28
 */
@Named
@Singleton
public class ConanProxyControllerV1
    extends ConanControllerV1
{
  @Inject
  private ConanProxyHandlers conanProxyHandlers;

  public void attach(final Router.Builder builder) {
    createRoute(builder, ConanRoutes.digest(), DIGEST, conanProxyHandlers.proxyHandler);
    createRoute(builder, ConanRoutes.searchQuery(), null, conanProxyHandlers.searchQueryHandler);
    createGetRoutes(builder, conanProxyHandlers.proxyHandler, conanProxyHandlers.proxyHandler, conanProxyHandlers.proxyHandler);
  }
}
