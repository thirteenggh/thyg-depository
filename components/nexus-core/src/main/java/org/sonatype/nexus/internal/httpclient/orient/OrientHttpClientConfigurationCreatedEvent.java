package org.sonatype.nexus.internal.httpclient.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.internal.httpclient.HttpClientConfigurationEvent;

/**
 * Emitted when a {@link HttpClientConfiguration} entity has been created.
 *
 * @since 3.1
 */
public class OrientHttpClientConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements HttpClientConfigurationEvent
{
  public OrientHttpClientConfigurationCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }
}
