package org.sonatype.nexus.internal.selector.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.internal.selector.SelectorConfigurationEvent;
import org.sonatype.nexus.selector.SelectorConfiguration;

/**
 * {@link SelectorConfiguration} created event.
 *
 * @since 3.1
 */
public class OrientSelectorConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements SelectorConfigurationEvent
{
  public OrientSelectorConfigurationCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public SelectorConfiguration getSelectorConfiguration() {
    return getEntity();
  }
}
