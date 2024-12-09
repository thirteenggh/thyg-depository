package org.sonatype.nexus.internal.httpclient;

import org.sonatype.nexus.datastore.api.SingletonDataAccess;

/**
 * {@link HttpClientConfigurationData} access.
 *
 * @since 3.21
 */
public interface HttpClientConfigurationDAO
    extends SingletonDataAccess<HttpClientConfigurationData>
{
  // no additional behaviour...
}
