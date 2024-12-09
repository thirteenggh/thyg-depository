package org.sonatype.nexus.repository.pypi.internal;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.proxy.ProxyHandler;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;

import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.isSearchRequest;

/**
 * PyPI-specific proxy handler.
 *
 * @since 3.1
 */
public class PyPiProxyHandler
    extends ProxyHandler
{
  @Override
  @Nullable
  protected Response buildMethodNotAllowedResponse(final Context context) {
    if (isSearchRequest(context.getRequest())) {
      return null;
    }
    return super.buildMethodNotAllowedResponse(context);
  }
}
