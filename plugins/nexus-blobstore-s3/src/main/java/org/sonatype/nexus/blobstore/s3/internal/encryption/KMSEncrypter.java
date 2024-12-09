package org.sonatype.nexus.blobstore.s3.internal.encryption;

import java.util.Optional;

import javax.inject.Named;

import com.amazonaws.services.s3.model.AbstractPutObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.SSEAwsKeyManagementParams;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;

/**
 * Adds KMS encryption to S3 requests.
 * The keyID is optional and in the params
 *
 * @since 3.19
 */
@Named(KMSEncrypter.ID)
public class KMSEncrypter
    implements S3Encrypter
{
  public static final String ID = "kmsManagedEncryption";

  public static final String NAME = "KMS Managed Encryption";

  private final SSEAwsKeyManagementParams kmsParameters;

  public KMSEncrypter() { this(empty()); }

  public KMSEncrypter(final Optional<String> kmsId) {
    this.kmsParameters = checkNotNull(kmsId)
        .map(String::trim)
        .filter(id -> !id.isEmpty())
        .map(SSEAwsKeyManagementParams::new)
        .orElse(new SSEAwsKeyManagementParams());
  }

  @Override
  public <T extends InitiateMultipartUploadRequest> T addEncryption(final T request) {
    request.setSSEAwsKeyManagementParams(kmsParameters);
    return request;
  }

  @Override
  public <T extends AbstractPutObjectRequest> T addEncryption(final T request) {
    request.setSSEAwsKeyManagementParams(kmsParameters);
    return request;
  }

  @Override
  public <T extends CopyObjectRequest> T addEncryption(final T request) {
    request.setSSEAwsKeyManagementParams(kmsParameters);
    return request;
  }
}
