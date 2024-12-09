package org.sonatype.nexus.repository.content.fluent.internal;

import java.util.Map;

import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentQuery;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link FluentQuery} implementation for {@link FluentAsset}s.
 *
 * @since 3.26
 */
public class FluentAssetQueryImpl
    implements FluentQuery<FluentAsset>
{
  private final FluentAssetsImpl assets;

  private final String kind;

  private final String filter;

  private final Map<String, Object> filterParams;

  FluentAssetQueryImpl(final FluentAssetsImpl assets, final String kind) {
    this.assets = checkNotNull(assets);
    this.kind = checkNotNull(kind);
    this.filter = null;
    this.filterParams = null;
  }

  FluentAssetQueryImpl(final FluentAssetsImpl assets,
                       final String filter,
                       final Map<String, Object> filterParams)
  {
    this.assets = checkNotNull(assets);
    this.kind = null;
    this.filter = checkNotNull(filter);
    this.filterParams = checkNotNull(filterParams);
  }

  @Override
  public int count() {
    return assets.doCount(kind, filter, filterParams);
  }

  @Override
  public Continuation<FluentAsset> browse(final int limit, final String continuationToken) {
    return assets.doBrowse(limit, continuationToken, kind, filter, filterParams);
  }
}
