package org.sonatype.nexus.repository.content.maintenance;

import java.util.Optional;
import java.util.Set;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;

/**
 * {@link ContentMaintenanceFacet} for formats where components have a one-to-one association with assets.
 *
 * @since 3.26
 */
@Named
public class SingleAssetMaintenanceFacet
    extends DefaultMaintenanceFacet
{
  @Override
  public Set<String> deleteAsset(final Asset asset) {
    Optional<Component> component = asset.component();
    if (component.isPresent()) {
      return super.deleteComponent(component.get());
    }

    return super.deleteAsset(asset);
  }
}
