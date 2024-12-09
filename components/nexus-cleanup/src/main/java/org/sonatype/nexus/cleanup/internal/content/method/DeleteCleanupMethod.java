package org.sonatype.nexus.cleanup.internal.content.method;

import java.util.function.BooleanSupplier;
import java.util.stream.StreamSupport;

import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.cleanup.internal.method.CleanupMethod;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.maintenance.ContentMaintenanceFacet;
import org.sonatype.nexus.repository.content.store.InternalIds;
import org.sonatype.nexus.repository.task.DeletionProgress;

/**
 * Provides a delete mechanism for cleanup
 *
 * @since 3.29
 */
@Named
public class DeleteCleanupMethod
    extends ComponentSupport
    implements CleanupMethod
{
  @Override
  public DeletionProgress run(
      final Repository repository,
      final Iterable<EntityId> components,
      final BooleanSupplier cancelledCheck)
  {
    ContentMaintenanceFacet maintenance = repository.facet(ContentMaintenanceFacet.class);
    DeletionProgress progress = new DeletionProgress();

    int[] componentIds = StreamSupport.stream(components.spliterator(), true)
        .mapToInt(InternalIds::toInternalId)
        .toArray();

    progress.addCount(maintenance.deleteComponents(componentIds));

    return progress;
  }
}
