package org.sonatype.nexus.internal.httpclient;

import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;

/**
 * Event for {@link HttpClientConfiguration} entity.
 * 
 * @since 3.1
 */
public interface HttpClientConfigurationEvent
{
  boolean isLocal();

  String getRemoteNodeId();
}
