package org.sonatype.nexus.repository.pypi.datastore;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * @since 3.29
 */
@Facet.Exposed
public interface PypiContentFacet
    extends ContentFacet
{
  Iterable<FluentAsset> browseAssets();

  Optional<FluentAsset> getAsset(final String path);

  boolean delete(final String path);

  FluentAsset findOrCreateAsset(
      final String packagePath,
      final FluentComponent component,
      final String assetKind);

  FluentAsset findOrCreateAsset(
      final String packagePath,
      final String assetKind);

  boolean isComponentExists(final String name);

  List<FluentAsset> assetsByComponentName(final String name);

  FluentComponent findOrCreateComponent(
      final String name,
      final String version,
      final String normalizedName);

  TempBlob getTempBlob(final Payload payload);

  TempBlob getTempBlob(final InputStream content, @Nullable final String contentType);
}
