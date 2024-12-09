package org.sonatype.repository.helm.internal.orient;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.repository.helm.HelmAttributes;
import org.sonatype.repository.helm.internal.AssetKind;

/**
 * @since 3.28
 */
@Facet.Exposed
public interface HelmFacet
    extends Facet
{
  Iterable<Asset> browseComponentAssets(final StorageTx tx, @Nullable final AssetKind assetKind);

  Optional<Asset> findAsset(final StorageTx tx, final String assetName);

  Asset findOrCreateAsset(
      final StorageTx tx,
      final String assetPath,
      final AssetKind assetKind,
      final HelmAttributes helmAttributes);

  Content saveAsset(
      final StorageTx tx,
      final Asset asset,
      final TempBlob contentSupplier,
      final Payload payload);

  Content saveAsset(
      final StorageTx tx,
      final Asset asset,
      final TempBlob contentSupplier,
      @Nullable final String contentType,
      @Nullable final AttributesMap contentAttributes) throws IOException;

  Content toContent(final Asset asset, final Blob blob);
}
