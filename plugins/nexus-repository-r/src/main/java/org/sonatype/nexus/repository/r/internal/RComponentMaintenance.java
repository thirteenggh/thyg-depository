package org.sonatype.nexus.repository.r.internal;

import java.util.Collections;
import java.util.Set;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.DefaultComponentMaintenanceImpl;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalDeleteBlob;
import org.sonatype.nexus.transaction.UnitOfWork;

import com.google.common.collect.Iterables;

/**
 * R Component and asset removing implementation
 *
 * @since 3.28
 */
public class RComponentMaintenance
    extends DefaultComponentMaintenanceImpl
{
  /**
   * Deletes the asset and its component if it's the only asset in it.
   */
  @Override
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

    final Component component = tx.findComponent(componentId);
    if (Iterables.size(tx.browseAssets(component)) == 1) {
      // Component with only one asset should be deleted as well with its asset
      return deleteComponentTx(componentId, deleteBlob).getAssets();
    }
    else {
      return super.deleteAssetTx(assetId, deleteBlob);
    }
  }
}
