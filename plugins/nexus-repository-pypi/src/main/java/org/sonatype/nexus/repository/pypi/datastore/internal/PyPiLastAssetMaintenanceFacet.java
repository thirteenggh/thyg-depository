package org.sonatype.nexus.repository.pypi.datastore.internal;

import java.util.Set;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.maintenance.ContentMaintenanceFacet;
import org.sonatype.nexus.repository.content.maintenance.LastAssetMaintenanceFacet;
import org.sonatype.nexus.repository.pypi.internal.AssetKind;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;
import org.sonatype.nexus.repository.pypi.internal.PyPiIndexFacet;

import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.P_NAME;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;

/**
 * {@link ContentMaintenanceFacet} for PyPI where components should be deleted along with their last asset
 * and associated index file.
 *
 * @since 3.29
 */
@Named
public class PyPiLastAssetMaintenanceFacet
    extends LastAssetMaintenanceFacet
{
  @Override
  public Set<String> deleteAsset(final Asset asset) {
    PyPiIndexFacet facet = getRepository().facet(PyPiIndexFacet.class);
    facet.deleteRootIndex();
    if (assetKindIsPackage(asset)) {
      // We are deleting a package and therefore need to remove the associated index as it is no longer valid
      facet.deleteIndex(asset.attributes(PyPiFormat.NAME).get(P_NAME, String.class));
    }
    return super.deleteAsset(asset);
  }

  @Override
  public Set<String> deleteComponent(final Component component) {
    PyPiIndexFacet facet = getRepository().facet(PyPiIndexFacet.class);
    facet.deleteRootIndex();
    facet.deleteIndex(component.name());

    return super.deleteComponent(component);
  }

  private boolean assetKindIsPackage(final Asset asset) {
    return AssetKind.PACKAGE.name().equals(asset.attributes(PyPiFormat.NAME).get(P_ASSET_KIND));
  }
}
