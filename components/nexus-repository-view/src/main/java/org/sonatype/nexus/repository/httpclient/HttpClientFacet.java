package org.sonatype.nexus.repository.httpclient;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

/**
 * HTTP client facet.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface HttpClientFacet
  extends Facet
{
  HttpClient getHttpClient();

  RemoteConnectionStatus getStatus();

  Header createBasicAuthHeader();

  /**
   * @since 3.20
   */
  @Nullable
  String getBearerToken();
}
