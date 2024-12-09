package org.sonatype.nexus.repository.content.internal.purge;

import javax.inject.Named;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.store.AssetStore;
import org.sonatype.nexus.repository.content.store.ComponentStore;
import org.sonatype.nexus.repository.purge.PurgeUnusedFacet;

import static com.google.common.base.Preconditions.checkArgument;
import static org.sonatype.nexus.repository.FacetSupport.State.STARTED;

/**
 * @since 3.24
 */
@FeatureFlag(name = "nexus.datastore.enabled")
@Named
public class PurgeUnusedFacetImpl
    extends FacetSupport
    implements PurgeUnusedFacet
{
  @Override
  @Guarded(by = STARTED)
  public void purgeUnused(final int numberOfDays) {
    checkArgument(numberOfDays > 0, "Number of days must be greater then zero");
    log.info("Purging unused components from repository {}", getRepository().getName());

    ContentFacetSupport contentFacet = (ContentFacetSupport) getRepository().facet(ContentFacet.class);
    ComponentStore<?> componentStore = contentFacet.stores().componentStore;
    AssetStore<?> assetStore = contentFacet.stores().assetStore;
    int contentRepositoryId = contentFacet.contentRepositoryId();

    int purgedAssets = assetStore.purgeNotRecentlyDownloaded(contentRepositoryId, numberOfDays);
    log.debug("Deleted {} unused assets without components", purgedAssets);

    int purgedComponents = componentStore.purgeNotRecentlyDownloaded(contentRepositoryId, numberOfDays);
    log.debug("Deleted {} unused components and their assets", purgedComponents);

    log.info("Purged {} unused components from repository {}", purgedComponents, getRepository().getName());
  }
}
