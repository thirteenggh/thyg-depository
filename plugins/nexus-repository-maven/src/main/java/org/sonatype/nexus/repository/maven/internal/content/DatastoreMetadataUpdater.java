package org.sonatype.nexus.repository.maven.internal.content;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPath.HashType;
import org.sonatype.nexus.repository.maven.internal.Constants;
import org.sonatype.nexus.repository.maven.internal.MavenMimeRulesSource;
import org.sonatype.nexus.repository.maven.internal.MavenModels;
import org.sonatype.nexus.repository.maven.internal.hosted.metadata.AbstractMetadataUpdater;
import org.sonatype.nexus.repository.view.payloads.BytesPayload;
import org.sonatype.nexus.repository.view.payloads.StringPayload;

import org.apache.maven.artifact.repository.metadata.Metadata;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @since 3.26
 */
public class DatastoreMetadataUpdater
    extends AbstractMetadataUpdater
{
  public DatastoreMetadataUpdater(final boolean update, final Repository repository) {
    super(update, repository);
  }

  @Override
  protected void write(final MavenPath mavenPath, final Metadata metadata) throws IOException {
    MavenContentFacet mavenContentFacet = repository.facet(MavenContentFacet.class);
    final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    MavenModels.writeMetadata(buffer, metadata);
    mavenContentFacet.put(mavenPath, new BytesPayload(buffer.toByteArray(), MavenMimeRulesSource.METADATA_TYPE));

    final Optional<Map<String, String>> hashCodes = mavenContentFacet
        .assets()
        .path("/" + mavenPath.getPath())
        .find()
        .flatMap(Asset::blob)
        .map(AssetBlob::checksums);
    checkState(hashCodes.isPresent(), "hashCodes");

    for (HashType hashType : HashType.values()) {
      MavenPath checksumPath = mavenPath.hash(hashType);
      String hashCode = hashCodes.get().get(hashType.getHashAlgorithm().name());
      checkState(hashCode != null, "hashCode: type=%s", hashType);
      mavenContentFacet.put(checksumPath, new StringPayload(hashCode, Constants.CHECKSUM_CONTENT_TYPE));
    }
  }

  @Override
  protected Optional<Metadata> read(final MavenPath mavenPath) throws IOException {
    return repository.facet(MavenContentFacet.class)
        .get(mavenPath)
        .map(content -> {
          Metadata metadata = null;
          try {
            metadata = MavenModels.readMetadata(content.openInputStream());
          }
          catch (IOException e) {
            log.warn("Corrupted metadata {} @ {}", repository.getName(), mavenPath.getPath());
          }
          return metadata;
        });
  }

  @Override
  protected void delete(final MavenPath mavenPath) {
    checkNotNull(repository);
    checkNotNull(mavenPath);
    try {
      repository.facet(MavenContentFacet.class).deleteWithHashes(mavenPath);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
