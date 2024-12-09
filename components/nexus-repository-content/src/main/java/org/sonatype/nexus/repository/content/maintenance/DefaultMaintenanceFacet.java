package org.sonatype.nexus.repository.content.maintenance;

import java.util.Set;

import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.store.ComponentStore;

import com.google.common.collect.ImmutableSet;

/**
 * Default {@link ContentMaintenanceFacet} for formats that don't need additional bookkeeping.
 *
 * @since 3.26
 */
@Named
public class DefaultMaintenanceFacet
    extends FacetSupport
    implements ContentMaintenanceFacet
{
  @Override
  public Set<String> deleteComponent(final Component component) {
    ImmutableSet.Builder<String> deletedPaths = ImmutableSet.builder();

    FluentComponent componentToDelete = contentFacet().components().with(component);

    componentToDelete.assets().forEach(assetToDelete -> {
      if (assetToDelete.delete()) {
        deletedPaths.add(assetToDelete.path()); // only add paths which were deleted by us
      }
    });

    componentToDelete.delete(); // the component itself has no path

    return deletedPaths.build();
  }

  @Override
  public Set<String> deleteAsset(final Asset asset) {
    ImmutableSet.Builder<String> deletedPaths = ImmutableSet.builder();

    FluentAsset assetToDelete = contentFacet().assets().with(asset);

    if (assetToDelete.delete()) {
      deletedPaths.add(assetToDelete.path());
    }

    return deletedPaths.build();
  }

  protected ContentFacet contentFacet() {
    return facet(ContentFacet.class);
  }

  @Override
  public int deleteComponents(final int[] componentIds) {
    ContentFacetSupport contentFacet = (ContentFacetSupport) contentFacet();
    ComponentStore<?> componentStore = contentFacet.stores().componentStore;

    return componentStore.purge(contentFacet.contentRepositoryId(), componentIds);
  }
}
