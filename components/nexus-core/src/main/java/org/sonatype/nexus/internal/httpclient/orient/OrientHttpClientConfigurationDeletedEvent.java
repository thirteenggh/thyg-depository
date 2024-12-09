package org.sonatype.nexus.internal.httpclient.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.internal.httpclient.HttpClientConfigurationEvent;

/**
 * Emitted when a {@link HttpClientConfiguration} entity has been deleted.
 *
 * @since 3.1
 */
public class OrientHttpClientConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements HttpClientConfigurationEvent
{
  public OrientHttpClientConfigurationDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }
}
