package org.sonatype.nexus.cleanup.internal.orient.method;

import java.util.function.BooleanSupplier;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.cleanup.internal.method.CleanupMethod;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.ComponentMaintenance;
import org.sonatype.nexus.repository.storage.DefaultComponentMaintenanceImpl.DeletionProgress;

/**
 * Provides a delete mechanism for cleanup
 *
 * @since 3.14
 */
@Named
@Priority(Integer.MAX_VALUE)
public class OrientDeleteCleanupMethod
    extends ComponentSupport
    implements CleanupMethod
{
  private final int batchSize;

  @Inject
  public OrientDeleteCleanupMethod(@Named("${nexus.cleanup.batchSize:-100}") final int batchSize) {
    this.batchSize = batchSize;
  }

  @Override
  public DeletionProgress run(final Repository repository,
                              final Iterable<EntityId> components,
                              final BooleanSupplier cancelledCheck)
  {
    return repository.facet(ComponentMaintenance.class).deleteComponents(components, cancelledCheck, batchSize);
  }
}
