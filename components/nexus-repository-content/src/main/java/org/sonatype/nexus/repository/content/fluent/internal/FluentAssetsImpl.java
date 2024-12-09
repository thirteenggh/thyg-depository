package org.sonatype.nexus.repository.content.fluent.internal;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssetBuilder;
import org.sonatype.nexus.repository.content.fluent.FluentAssets;
import org.sonatype.nexus.repository.content.fluent.FluentQuery;
import org.sonatype.nexus.repository.content.store.AssetStore;
import org.sonatype.nexus.repository.content.store.InternalIds;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.types.GroupType;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.content.fluent.internal.RepositoryContentUtil.getLeafRepositoryIds;
import static org.sonatype.nexus.repository.content.fluent.internal.RepositoryContentUtil.isGroupRepository;
import static org.sonatype.nexus.repository.content.store.InternalIds.contentRepositoryId;
import static org.sonatype.nexus.repository.content.store.InternalIds.toInternalId;

/**
 * {@link FluentAssets} implementation.
 *
 * @since 3.24
 */
public class FluentAssetsImpl
    implements FluentAssets
{
  private final ContentFacetSupport facet;

  private final AssetStore<?> assetStore;

  public FluentAssetsImpl(final ContentFacetSupport facet, final AssetStore<?> assetStore) {
    this.facet = checkNotNull(facet);
    this.assetStore = checkNotNull(assetStore);
  }

  @Override
  public FluentAssetBuilder path(final String path) {
    return new FluentAssetBuilderImpl(facet, assetStore, path);
  }

  @Override
  public FluentAsset with(final Asset asset) {
    return asset instanceof FluentAsset ? (FluentAsset) asset
        : new FluentAssetImpl(facet, asset);
  }

  @Override
  public int count() {
    return doCount(null, null, null);
  }

  int doCount(@Nullable final String kind,
              @Nullable final String filter,
              @Nullable final Map<String, Object> filterParams)
  {
    return assetStore.countAssets(facet.contentRepositoryId(), kind, filter, filterParams);
  }

  @Override
  public Continuation<FluentAsset> browse(final int limit, final String continuationToken) {
    return doBrowse(limit, continuationToken, null, null, null);
  }

  Continuation<FluentAsset> doBrowse(final int limit,
                                     @Nullable final String continuationToken,
                                     @Nullable final String kind,
                                     @Nullable final String filter,
                                     @Nullable final Map<String, Object> filterParams)
  {
    if (isGroupRepository(facet.repository())) {
      Set<Integer> leafRepositoryIds = getLeafRepositoryIds(facet.repository());
      if (!leafRepositoryIds.isEmpty()) {
        return new FluentContinuation<>(assetStore.browseAssets(leafRepositoryIds, limit, continuationToken),
            this::with);
      }
    }
    return new FluentContinuation<>(assetStore.browseAssets(facet.contentRepositoryId(),
        limit, continuationToken, kind, filter, filterParams), this::with);
  }

  @Override
  public FluentQuery<FluentAsset> byKind(final String kind) {
    return new FluentAssetQueryImpl(this, kind);
  }

  @Override
  public FluentQuery<FluentAsset> byFilter(final String filter, final Map<String, Object> filterParams) {
    return new FluentAssetQueryImpl(this, filter, filterParams);
  }

  @Override
  public Optional<FluentAsset> find(final EntityId externalId) {
    return assetStore.readAsset(toInternalId(externalId))
        .filter(this::containedInRepository)
        .map(asset -> new FluentAssetImpl(facet, asset));
  }

  /**
   * Returns {@code true} if this asset is contained in this repository or any of its members.
   */
  private boolean containedInRepository(final Asset asset) {
    int expectedContentRepositoryId = contentRepositoryId(asset);
    if (expectedContentRepositoryId == facet.contentRepositoryId()) {
      return true;
    }
    else if (facet.repository().getType() instanceof GroupType) {
      return facet.repository().facet(GroupFacet.class).allMembers().stream()
          .map(InternalIds::contentRepositoryId)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .anyMatch(id -> id == expectedContentRepositoryId);
    }
    return false;
  }
}
