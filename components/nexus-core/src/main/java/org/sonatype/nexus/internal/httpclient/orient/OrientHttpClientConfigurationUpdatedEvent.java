package org.sonatype.nexus.internal.httpclient.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.internal.httpclient.HttpClientConfigurationEvent;

/**
 * Emitted when a {@link HttpClientConfiguration} entity has been updated.
 *
 * @since 3.1
 */
public class OrientHttpClientConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements HttpClientConfigurationEvent
{
  public OrientHttpClientConfigurationUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }
}
