package org.sonatype.nexus.internal.selector.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.internal.selector.SelectorConfigurationEvent;
import org.sonatype.nexus.selector.SelectorConfiguration;

/**
 * {@link SelectorConfiguration} deleted event.
 *
 * @since 3.1
 */
public class OrientSelectorConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements SelectorConfigurationEvent
{
  public OrientSelectorConfigurationDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public SelectorConfiguration getSelectorConfiguration() {
    return getEntity();
  }
}
