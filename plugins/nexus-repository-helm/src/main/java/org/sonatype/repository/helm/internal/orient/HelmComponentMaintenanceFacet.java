package org.sonatype.repository.helm.internal.orient;

import java.util.Collections;
import java.util.Set;

import javax.inject.Named;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.DefaultComponentMaintenanceImpl;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.UnitOfWork;

import com.orientechnologies.common.concur.ONeedRetryException;

/**
 * @since 3.28
 */
@Named
public class HelmComponentMaintenanceFacet
    extends DefaultComponentMaintenanceImpl
{
  @Transactional(retryOn = ONeedRetryException.class)
  @Override
  protected Set<String> deleteAssetTx(final EntityId assetId, final boolean deleteBlobs) {
    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());
    Asset asset = tx.findAsset(assetId, bucket);

    if (asset == null) {
      return Collections.emptySet();
    }

    tx.deleteAsset(asset, deleteBlobs);

    if (asset.componentId() != null) {
      Component component = tx.findComponentInBucket(asset.componentId(), bucket);

      if (!tx.browseAssets(component).iterator().hasNext()) {
        log.debug("Deleting component: {}", component);
        tx.deleteComponent(component, deleteBlobs);
      }
    }
    return Collections.singleton(asset.name());
  }
}
