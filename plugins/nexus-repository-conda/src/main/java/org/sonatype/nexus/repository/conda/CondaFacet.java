package org.sonatype.nexus.repository.conda;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

import java.util.function.Supplier;

/**
 * @since 3.19
 */
@Facet.Exposed
public interface CondaFacet
    extends Facet
{
  /**
   * Find a component by its name and tag (version)
   *
   * @return found component or null if not found
   */
  @Nullable
  Component findComponent(final StorageTx tx,
                          final Repository repository,
                          final String arch,
                          final String name,
                          final String version);

  /**
   * Find an asset by its name.
   *
   * @return found asset or null if not found
   */
  @Nullable
  Asset findAsset(final StorageTx tx, final Bucket bucket, final String assetName);

  /**
   * Save an asset and create blob.
   *
   * @return blob content
   */
  Content saveAsset(final StorageTx tx,
                    final Asset asset,
                    final Supplier<InputStream> contentSupplier,
                    final Payload payload) throws IOException;

  /**
   * Save an asset and create blob.
   *
   * @return blob content
   */
  Content saveAsset(final StorageTx tx,
                    final Asset asset,
                    final Supplier<InputStream> contentSupplier,
                    final String contentType,
                    @Nullable final AttributesMap contentAttributes) throws IOException;

  /**
   * Convert an asset blob to {@link Content}.
   *
   * @return content of asset blob
   */
  Content toContent(final Asset asset, final Blob blob);
}
