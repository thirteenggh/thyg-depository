package org.sonatype.nexus.content.example.internal.recipe;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.content.example.ExampleContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.store.FormatStoreManager;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.collect.ImmutableList;

import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA256;

/**
 * Provides persistent content for an 'example' format.
 *
 * @since 3.24
 */
@Named(ExampleFormat.NAME)
public class ExampleContentFacetImpl
    extends ContentFacetSupport
    implements ExampleContentFacet
{
  private static final Iterable<HashAlgorithm> HASHING = ImmutableList.of(SHA256);

  @Inject
  public ExampleContentFacetImpl(@Named(ExampleFormat.NAME) final FormatStoreManager formatStoreManager) {
    super(formatStoreManager);
  }

  @Override
  public Optional<Content> get(final String path) {
    return assets().path(path).find().map(FluentAsset::download);
  }

  @Override
  public Content put(final String path, final Payload content) throws IOException {
    try (TempBlob blob = blobs().ingest(content, HASHING)) {
      return assets().path(path).getOrCreate().attach(blob).markAsCached(content).download();
    }
  }

  @Override
  public boolean delete(final String path) throws IOException {
    return assets().path(path).find().map(FluentAsset::delete).orElse(false);
  }
}
