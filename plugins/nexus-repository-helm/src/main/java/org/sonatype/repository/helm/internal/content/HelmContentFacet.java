package org.sonatype.repository.helm.internal.content;

import java.io.IOException;
import java.util.Optional;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.repository.helm.HelmAttributes;
import org.sonatype.repository.helm.internal.AssetKind;

/**
 * @since 3.28
 */
@Facet.Exposed
public interface HelmContentFacet
    extends ContentFacet
{
  Iterable<FluentAsset> browseAssets();

  Optional<Content> getAsset(String path);

  Content putIndex(String path, Content content, AssetKind assetKind);

  Content putComponent(String path, Content content, AssetKind assetKind) throws IOException;

  Content putComponent(String path, TempBlob tempBlob, HelmAttributes helmAttrs, Content content, AssetKind assetKind);

  TempBlob getTempBlob(Payload payload);

  boolean delete(String path);
}
