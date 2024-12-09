package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityBatchEvent.Batchable;

/**
 * Component event.
 *
 * @since 3.0
 */
public interface ComponentEvent
    extends Batchable
{
  /**
   * @since 3.1
   */
  boolean isLocal();

  String getRepositoryName();

  EntityId getComponentId();

  Component getComponent();
}
