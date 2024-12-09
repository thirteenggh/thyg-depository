package org.sonatype.nexus.internal.httpclient

import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration
import org.sonatype.nexus.httpclient.config.ConnectionConfiguration
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration
import org.sonatype.nexus.httpclient.config.ProxyConfiguration

import org.apache.http.client.AuthenticationStrategy
import org.apache.http.client.RedirectStrategy

class TestHttpClientConfiguration
    implements HttpClientConfiguration
{
  ConnectionConfiguration connection

  ProxyConfiguration proxy

  RedirectStrategy redirectStrategy

  AuthenticationConfiguration authentication;

  AuthenticationStrategy authenticationStrategy;

  Boolean normalizeUri

  Boolean disableContentCompression;

  Boolean getNormalizeUri() {
    return Optional.ofNullable(normalizeUri)
  }

  void setNormalizeUri(Boolean normalizeUri) {
    this.normalizeUri = normalizeUri
  }

  TestHttpClientConfiguration copy() {
    return this
  }
}
