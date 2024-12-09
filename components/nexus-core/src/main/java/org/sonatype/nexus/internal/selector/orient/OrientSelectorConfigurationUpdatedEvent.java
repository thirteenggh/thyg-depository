package org.sonatype.nexus.internal.selector.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.internal.selector.SelectorConfigurationEvent;
import org.sonatype.nexus.selector.SelectorConfiguration;

/**
 * {@link SelectorConfiguration} updated event.
 *
 * @since 3.1
 */
public class OrientSelectorConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements SelectorConfigurationEvent
{
  public OrientSelectorConfigurationUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public SelectorConfiguration getSelectorConfiguration() {
    return getEntity();
  }
}
