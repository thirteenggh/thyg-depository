package org.sonatype.nexus.blobstore.s3.rest.internal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.s3.rest.internal.model.S3BlobStoreApiModel;
import org.sonatype.nexus.rest.ValidationErrorsException;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.sonatype.nexus.blobstore.s3.internal.S3BlobStore.TYPE;

/**
 * Performs validation checks on specified {@link S3BlobStoreApiModel} object containing updates to an S3 blob store.
 *
 * @since 3.20
 */
@Named
@Singleton
public class S3BlobStoreApiUpdateValidation
{
  private static final String BLOB_STORE_NAME = "name";

  static final String BLOB_STORE_NAME_UPDATE_ERROR_MESSAGE = "Renaming an S3 blob store name is not supported";

  static final String NON_EXISTENT_BLOB_STORE_ERROR_MESSAGE_FORMAT = "No S3 blob store called '%s'";

  static final String BLOB_STORE_TYPE_MISMATCH_ERROR_FORMAT = "Blob store %s is not an S3 blob store";

  private static final String COMMA = ",";

  private final BlobStoreManager blobStoreManager;

  @Inject
  public S3BlobStoreApiUpdateValidation(final BlobStoreManager blobStoreManager) {
    this.blobStoreManager = blobStoreManager;
  }

  void validateUpdateRequest(final S3BlobStoreApiModel s3BlobStoreApiModel, final String blobStoreName) {
    List<String> errors = new ArrayList<>();
    final boolean blobStoreExists = checkBlobStoreExists(blobStoreName, errors);
    checkBlobStoreNamesMatch(s3BlobStoreApiModel, blobStoreName, errors);
    if (blobStoreExists) {
      checkBlobStoreTypeIsS3(blobStoreName, errors);
    }

    if (!errors.isEmpty()) {
      throw new ValidationErrorsException(BLOB_STORE_NAME, join(COMMA, errors));
    }
  }

  private boolean checkBlobStoreExists(final String blobStoreName, final List<String> errors) {
    if (!blobStoreManager.exists(blobStoreName)) {
      errors.add(format(NON_EXISTENT_BLOB_STORE_ERROR_MESSAGE_FORMAT, blobStoreName));
      return false;
    }
    return true;
  }

  private void checkBlobStoreNamesMatch(
      final S3BlobStoreApiModel s3BlobStoreApiModel,
      final String blobStoreName, final List<String> errors)
  {
    if (!equalsIgnoreCase(s3BlobStoreApiModel.getName(), blobStoreName)) {
      errors.add(BLOB_STORE_NAME_UPDATE_ERROR_MESSAGE);
    }
  }

  private void checkBlobStoreTypeIsS3(final String blobStoreName, final List<String> errors) {
    if (existingBlobStoreIsNotS3(blobStoreName)) {
      errors.add(format(BLOB_STORE_TYPE_MISMATCH_ERROR_FORMAT, blobStoreName));
    }
  }

  private boolean existingBlobStoreIsNotS3(final String blobStoreName) {
    return !ofNullable(blobStoreManager.get(blobStoreName))
        .map(BlobStore::getBlobStoreConfiguration)
        .map(BlobStoreConfiguration::getType)
        .filter(type -> equalsIgnoreCase(TYPE, type))
        .isPresent();
  }
}
