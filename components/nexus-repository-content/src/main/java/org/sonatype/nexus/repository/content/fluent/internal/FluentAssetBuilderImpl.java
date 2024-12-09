package org.sonatype.nexus.repository.content.fluent.internal;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.sonatype.nexus.common.time.UTC;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssetBuilder;
import org.sonatype.nexus.repository.content.store.AssetData;
import org.sonatype.nexus.repository.content.store.AssetStore;
import org.sonatype.nexus.repository.proxy.ProxyFacetSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link FluentAssetBuilder} implementation.
 *
 * @since 3.24
 */
public class FluentAssetBuilderImpl
    implements FluentAssetBuilder
{
  private final ContentFacetSupport facet;

  private final AssetStore<?> assetStore;

  private final String path;

  private String kind = "";

  private Component component;

  public FluentAssetBuilderImpl(final ContentFacetSupport facet, final AssetStore<?> assetStore, final String path) {
    this.facet = checkNotNull(facet);
    this.assetStore = checkNotNull(assetStore);
    this.path = checkNotNull(path);
  }

  @Override
  public FluentAssetBuilder kind(final String kind) {
    this.kind = checkNotNull(kind);
    return this;
  }

  @Override
  public FluentAssetBuilder component(final Component component) {
    this.component = checkNotNull(component);
    return this;
  }

  @Override
  public FluentAsset getOrCreate() {
    return new FluentAssetImpl(facet, assetStore.getOrCreate(this::findAsset, this::createAsset));
  }

  @Override
  public Optional<FluentAsset> find() {
    return findAsset().map(asset -> new FluentAssetImpl(facet, asset));
  }

  private Optional<Asset> findAsset() {
    return assetStore.readPath(facet.contentRepositoryId(), path);
  }

  private Asset createAsset() {
    AssetData asset = new AssetData();
    asset.setRepositoryId(facet.contentRepositoryId());
    asset.setPath(path);
    asset.setKind(kind);
    asset.setComponent(component);

    OffsetDateTime now = UTC.now();
    asset.setLastUpdated(now);

    if (ProxyFacetSupport.isDownloading()) {
      asset.setLastDownloaded(now);
    }

    assetStore.createAsset(asset);

    return asset;
  }
}
