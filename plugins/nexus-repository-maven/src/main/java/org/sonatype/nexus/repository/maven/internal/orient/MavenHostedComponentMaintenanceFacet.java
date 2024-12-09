package org.sonatype.nexus.repository.maven.internal.orient;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;

import javax.annotation.Nullable;
import javax.inject.Named;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.orient.maven.OrientMavenFacet;
import org.sonatype.nexus.repository.maven.MavenHostedFacet;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentMaintenance;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalDeleteBlob;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.UnitOfWork;

import static java.util.stream.Collectors.toList;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_ARTIFACT_ID;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_BASE_VERSION;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_GROUP_ID;

/**
 * maven format specific hosted {@link ComponentMaintenance}.
 *
 * @since 3.0
 */
@Named
public class MavenHostedComponentMaintenanceFacet
    extends MavenComponentMaintenanceFacet
{
  @Override
  public Set<String> deleteComponent(final EntityId componentId, final boolean deleteBlobs) {
    String[] coordinates = Transactional.operation
        .withDb(getRepository().facet(StorageFacet.class).txSupplier())
        .call(() -> this.findComponent(componentId));
    Set<String> deletedAssets = super.deleteComponent(componentId, deleteBlobs);
    if (coordinates != null) {
      MavenHostedFacet facet = getRepository().facet(MavenHostedFacet.class);
      Set<String> deletedMetadataPaths = facet.deleteMetadata(coordinates[0], coordinates[1], coordinates[2]);
      deletedAssets.addAll(deletedMetadataPaths);
    }
    return deletedAssets;
  }

  @Override
  @TransactionalDeleteBlob
  protected DeletionProgress doBatchDelete(final List<EntityId> entityIds, final BooleanSupplier cancelledCheck) {
    try {
      List<String[]> gavs = collectGavs(entityIds);
      OrientMavenFacet mavenFacet = getRepository().facet(OrientMavenFacet.class);
      final StorageTx tx = UnitOfWork.currentTx();
      Bucket bucket = tx.findBucket(getRepository());

      DeletionProgress batchProgress = deleteComponentBatch(entityIds, cancelledCheck);

      for (String[] gav : gavs) {
        mavenFacet.maybeDeleteOrFlagToRebuildMetadata(bucket, gav[0], gav[1], gav[2]);
        mavenFacet.maybeDeleteOrFlagToRebuildMetadata(bucket, gav[0], gav[1]);
        mavenFacet.maybeDeleteOrFlagToRebuildMetadata(bucket, gav[0]);
      }

      return batchProgress;
    }
    catch (Exception ex) {
      log.debug("Error encountered attempting to delete components for repository {}.", getRepository().getName(), ex);
    }
    return new DeletionProgress();
  }

  @Transactional
  protected List<String[]> collectGavs(final List<EntityId> entityIds) {
    return entityIds.stream()
        .map(this::findComponent)
        .filter(Objects::nonNull)
        .collect(toList());
  }

  @Nullable
  private String[] findComponent(final EntityId entityId) {
    final StorageTx tx = UnitOfWork.currentTx();
    Component component = tx.findComponentInBucket(entityId, tx.findBucket(getRepository()));
    if (component != null) {
      return new String[]{
          component.formatAttributes().get(P_GROUP_ID, String.class),
          component.formatAttributes().get(P_ARTIFACT_ID, String.class),
          component.formatAttributes().get(P_BASE_VERSION, String.class)
      };
    }
    return null; //NOSONAR
  }
}
