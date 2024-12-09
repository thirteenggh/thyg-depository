package org.sonatype.nexus.repository.content.maintenance;

import java.util.Optional;
import java.util.Set;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;

/**
 * {@link ContentMaintenanceFacet} for formats where components should be deleted along with their last asset.
 *
 * @since 3.26
 */
@Named
public class LastAssetMaintenanceFacet
    extends DefaultMaintenanceFacet
{
  @Override
  public Set<String> deleteAsset(final Asset asset) {
    Optional<Component> component = asset.component();
    if (component.isPresent()) {
      FluentComponent componentToDelete = contentFacet().components().with(component.get());
      if (componentToDelete.assets().size() <= 1) {
        return super.deleteComponent(componentToDelete);
      }
    }

    return super.deleteAsset(asset);
  }
}
