package org.sonatype.nexus.repository.apt;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.apt.internal.debian.PackageInfo;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

/**
 * @since 3.17
 */
@Facet.Exposed
public interface AptFacet
    extends Facet
{
  @Nullable
  Content get(final String path) throws IOException;

  Content put(final String path, final Payload payload) throws IOException;

  Content put(final String path, final Payload payload, @Nullable final PackageInfo packageInfo) throws IOException;

  boolean delete(final String path) throws IOException;

  Asset findOrCreateDebAsset(final StorageTx tx, final String path, final PackageInfo packageInfo);

  Asset findOrCreateMetadataAsset(final StorageTx tx, final String path);

  boolean isFlat();

  String getDistribution();
}
