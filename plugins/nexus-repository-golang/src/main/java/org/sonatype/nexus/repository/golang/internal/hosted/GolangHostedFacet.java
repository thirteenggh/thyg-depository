package org.sonatype.nexus.repository.golang.internal.hosted;

import java.io.IOException;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Facet.Exposed;
import org.sonatype.nexus.repository.golang.AssetKind;
import org.sonatype.nexus.repository.golang.internal.metadata.GolangAttributes;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

/**
 * @since 3.17
 */
@Exposed
public interface GolangHostedFacet
    extends Facet
{
  Content getPackage(final String path);

  Content getMod(final String path);

  Content getInfo(final String path, final GolangAttributes golangAttributes);

  Content getList(final String module);

  void upload(final String path, final GolangAttributes golangAttributes, final Payload payload, final AssetKind assetKind)
      throws IOException;
}
