package org.sonatype.nexus.repository.maven;

import java.util.Optional;

import org.sonatype.nexus.repository.upload.AssetUpload;
import org.sonatype.nexus.repository.upload.ComponentUpload;
import org.sonatype.nexus.repository.upload.UploadDefinition;
import org.sonatype.nexus.repository.upload.ValidatingComponentUpload;

import static org.sonatype.nexus.common.text.Strings2.isBlank;

/**
 * A holder of {@link ComponentUpload} that's meant to validate it based on provided {@link UploadDefinition} for Maven
 *
 * @since 3.8
 */
public class MavenValidatingComponentUpload
    extends ValidatingComponentUpload
{
  private static final String EXTENSION = "extension";

  private static final String CLASSIFIER = "classifier";

  public MavenValidatingComponentUpload(final UploadDefinition uploadDefinition,
                                        final ComponentUpload componentUpload)
  {
    super(uploadDefinition, componentUpload);
  }

  @Override
  protected void validateRequiredComponentFieldPresent() {
    if (!findPomAsset().isPresent()) {
      super.validateRequiredComponentFieldPresent();
    }
  }

  private Optional<AssetUpload> findPomAsset() {
    return componentUpload.getAssetUploads().stream()
        .filter(asset -> "pom".equals(asset.getField(EXTENSION)) && isBlank(asset.getField(CLASSIFIER)))
        .findFirst();
  }
}
