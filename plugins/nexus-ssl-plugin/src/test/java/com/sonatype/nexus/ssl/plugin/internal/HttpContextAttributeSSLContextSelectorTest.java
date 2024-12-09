package com.sonatype.nexus.ssl.plugin.internal;

import javax.net.ssl.SSLContext;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.httpclient.SSLContextSelector;
import org.sonatype.nexus.ssl.TrustStore;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;

public class HttpContextAttributeSSLContextSelectorTest
    extends TestSupport
{
  @Mock
  private TrustStore trustStore;

  @Mock
  private SSLContext sslContext;

  private HttpContext httpContext;

  private HttpContextAttributeSSLContextSelector sslContextSelector;

  @Before
  public void setUp() {
    when(trustStore.getSSLContext()).thenReturn(sslContext);
    httpContext = new BasicHttpContext();
    sslContextSelector = new HttpContextAttributeSSLContextSelector(trustStore);
  }

  @Test
  public void testSelect_AttributeIsNull() {
    assertThat(sslContextSelector.select(httpContext), is(nullValue()));
  }

  @Test
  public void testSelect_AttributeIsFalse() {
    httpContext.setAttribute(SSLContextSelector.USE_TRUST_STORE, false);
    assertThat(sslContextSelector.select(httpContext), is(nullValue()));
  }

  @Test
  public void testSelect_AttributeIsTrue() {
    httpContext.setAttribute(SSLContextSelector.USE_TRUST_STORE, true);
    assertThat(sslContextSelector.select(httpContext), is(sslContext));
  }
}
