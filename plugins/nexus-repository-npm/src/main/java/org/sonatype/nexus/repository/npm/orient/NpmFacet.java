package org.sonatype.nexus.repository.npm.orient;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetBlob;

/**
 * NPM facet, present on all NPM repositories.
 *
 * @since 3.6.1
 */
@Facet.Exposed
public interface NpmFacet
    extends Facet
{
  @Nullable
  Asset findRepositoryRootAsset();

  @Nullable
  Asset putRepositoryRoot(AssetBlob assetBlob, @Nullable AttributesMap contentAttributes) throws IOException;


  @Nullable
  Asset findPackageRootAsset(String packageId);

  @Nullable
  Asset putPackageRoot(String packageId, AssetBlob assetBlob, @Nullable AttributesMap contentAttributes)
      throws IOException;

  @Nullable
  Asset findTarballAsset(String packageId,
                         String tarballName);

  @Nullable
  Asset putTarball(String packageId,
                   String tarballName,
                   AssetBlob assetBlob,
                   @Nullable AttributesMap contentAttributes) throws IOException;
}
