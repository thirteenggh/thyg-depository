package org.sonatype.nexus.repository.maven.internal.content.importtask;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.importtask.ImportPostProcessor;
import org.sonatype.nexus.repository.maven.MavenPath.HashType;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;

/**
 * Provides some post processing capabilities for assets that could have hash files that need blobUpdated date to match
 * the imported asset blobUpdated Date
 *
 * @since 3.29
 */
@Singleton
@Named(Maven2Format.NAME)
public class MavenImportPostProcessor
    extends ComponentSupport
    implements ImportPostProcessor
{
  @Override
  public void attributePostProcessing(
      final Repository repository, final Asset asset)
  {
    for (HashType type : HashType.values()) {
      updateHashFile('.' + type.getExt(), asset, repository);
    }
  }

  private void updateHashFile(
      final String hashFileExtension,
      final Asset asset,
      final Repository repository)
  {
    Optional<FluentAsset> hashAsset = repository.facet(ContentFacet.class).assets()
        .path(asset.path() + hashFileExtension)
        .find();

    if (hashAsset.isPresent()) {
      updateHashFileDates(hashAsset.get(), asset);
    }
  }

  private void updateHashFileDates(final FluentAsset hashAsset, final Asset asset) {
    Optional<OffsetDateTime> lastDownloaded = hashAsset.lastDownloaded();
    if (!lastDownloaded.isPresent() ||
        !lastDownloaded.get().equals(asset.lastDownloaded().orElse(null))) {
      hashAsset.lastDownloaded(asset.lastDownloaded().orElse(null));
    }
    if (!hashAsset.created().equals(asset.created())) {
      hashAsset.created(asset.created());
    }
    if (hashAsset.lastUpdated() == null ||
        (hashAsset.lastUpdated() != null && !hashAsset.lastUpdated().equals(asset.lastUpdated()))) {
      hashAsset.lastUpdated(asset.lastUpdated());
    }

    hashAsset.blob().ifPresent(blob -> {
      asset.blob().ifPresent(assetBlob -> {
        if (blob.blobCreated() == null ||
            (blob.blobCreated() != null && !blob.blobCreated().equals(assetBlob.blobCreated()))) {
          hashAsset.blobCreated(assetBlob.blobCreated());
        }
      });
    });
  }
}
