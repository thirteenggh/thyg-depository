package org.sonatype.repository.helm.internal.orient.hosted;

import java.io.IOException;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Facet.Exposed;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.repository.helm.internal.AssetKind;

/**
 * Helm Hosted Facet
 *
 * @since 3.28
 */
@Exposed
public interface HelmHostedFacet
    extends Facet
{
  Content get(String path);

  String getPath(final TempBlob tempBlob, final AssetKind assetKind) throws IOException;

  void upload(final String path, final Payload payload, final AssetKind assetKind) throws IOException;

  Asset upload(final String path, final TempBlob tempBlob, final Payload payload, final AssetKind assetKind) throws IOException;

  boolean delete(String path);
}
