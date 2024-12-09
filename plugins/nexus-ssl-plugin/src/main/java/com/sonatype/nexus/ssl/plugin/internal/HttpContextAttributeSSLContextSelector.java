package com.sonatype.nexus.ssl.plugin.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;

import org.sonatype.nexus.ssl.TrustStore;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.httpclient.SSLContextSelector;

import org.apache.http.protocol.HttpContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An {@link SSLContextSelector} that will make use of Nexus SSL TrustStore depending on value of
 * {@link SSLContextSelector#USE_TRUST_STORE} attribute.
 *
 * @since 3.0
 */
@Named
@Singleton
public class HttpContextAttributeSSLContextSelector
    extends ComponentSupport
    implements SSLContextSelector
{
  private final TrustStore trustStore;

  @Inject
  public HttpContextAttributeSSLContextSelector(final TrustStore trustStore) {
    this.trustStore = checkNotNull(trustStore);
  }

  @Override
  public SSLContext select(final HttpContext context) {
    Object useTrustStore = context.getAttribute(SSLContextSelector.USE_TRUST_STORE);
    if (Boolean.TRUE.equals(useTrustStore)) {
      return trustStore.getSSLContext();
    }
    return null;
  }
}
