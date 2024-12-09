package org.sonatype.nexus.content.raw.internal.recipe;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.content.raw.RawContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.maintenance.ContentMaintenanceFacet;
import org.sonatype.nexus.repository.content.store.FormatStoreManager;
import org.sonatype.nexus.repository.raw.RawCoordinatesHelper;
import org.sonatype.nexus.repository.raw.internal.RawFormat;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.collect.ImmutableList;

import static org.sonatype.nexus.common.hash.HashAlgorithm.MD5;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;

/**
 * A {@link RawContentFacet} that persists to a {@link ContentFacet}.
 *
 * @since 3.24
 */
@Named(RawFormat.NAME)
public class RawContentFacetImpl
    extends ContentFacetSupport
    implements RawContentFacet
{
  private static final Iterable<HashAlgorithm> HASHING = ImmutableList.of(MD5, SHA1);

  @Inject
  public RawContentFacetImpl(@Named(RawFormat.NAME) final FormatStoreManager formatStoreManager) {
    super(formatStoreManager);
  }

  @Override
  public Optional<Content> get(final String path) throws IOException {
    return assets().path(path).find().map(FluentAsset::download);
  }

  @Override
  public Content put(final String path, final Payload content) throws IOException {
    try (TempBlob blob = blobs().ingest(content, HASHING)){
      return assets()
          .path(path)
          .component(components()
              .name(path)
              .namespace(RawCoordinatesHelper.getGroup(path))
              .getOrCreate())
          .getOrCreate()
          .attach(blob)
          .markAsCached(content)
          .download();
    }
  }

  @Override
  public boolean delete(final String path) throws IOException {
    return assets().path(path).find()
        .map(asset -> repository().facet(ContentMaintenanceFacet.class).deleteAsset(asset).contains(path))
        .orElse(false);
  }
}
