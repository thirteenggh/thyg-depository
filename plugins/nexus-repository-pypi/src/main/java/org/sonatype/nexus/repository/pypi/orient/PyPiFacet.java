package org.sonatype.nexus.repository.pypi.orient;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetBlob;

/**
 * @since 3.14
 */
@Facet.Exposed
public interface PyPiFacet
    extends Facet
{
  boolean assetExists(final String name);

  @Nullable
  Asset put(final String path, final AssetBlob assetBlob) throws IOException;

  Asset putPackage(final String path, final AssetBlob assetBlob) throws IOException;

  Asset putIndex(final String path, final AssetBlob assetBlob);
}
