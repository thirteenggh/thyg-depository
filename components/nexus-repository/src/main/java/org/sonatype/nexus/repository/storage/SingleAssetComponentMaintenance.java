package org.sonatype.nexus.repository.storage;

import java.util.Collections;
import java.util.Set;

import javax.inject.Named;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.transaction.TransactionalDeleteBlob;
import org.sonatype.nexus.transaction.UnitOfWork;

/**
 * A component maintenance facet that assumes that Components have the same lifecycle as their
 * single Assets.
 *
 * @since 3.0
 */
@Named
public class SingleAssetComponentMaintenance
    extends DefaultComponentMaintenanceImpl
{
  /**
   * Deletes both the asset and its component.
   */
  @TransactionalDeleteBlob
  protected Set<String> deleteAssetTx(final EntityId assetId, final boolean deleteBlob) {
    StorageTx tx = UnitOfWork.currentTx();
    final Asset asset = tx.findAsset(assetId, tx.findBucket(getRepository()));
    if (asset == null) {
      return Collections.emptySet();
    }
    final EntityId componentId = asset.componentId();
    if (componentId == null) {
      // Assets without components should be deleted on their own
      return super.deleteAssetTx(assetId, deleteBlob);
    }
    else {
      // Otherwise, delete the component, which in turn cascades down to the asset
      return deleteComponentTx(componentId, deleteBlob).getAssets();
    }
  }
}
