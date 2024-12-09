package org.sonatype.nexus.blobstore.s3.internal.encryption;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Named;

import com.amazonaws.services.s3.model.AbstractPutObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import static java.util.Optional.ofNullable;

/**
 * Adds S3 managed encryption to S3 requests.
 * @since 3.19
 */
@Named(S3ManagedEncrypter.ID)
public class S3ManagedEncrypter
    implements S3Encrypter
{
  public static final String ID = "s3ManagedEncryption";
  public static final String NAME = "S3 Managed Encryption";

  @Override
  public <T extends InitiateMultipartUploadRequest> T addEncryption(final T request) {
    setEncryption(request::getObjectMetadata, request::setObjectMetadata);
    return request;
  }

  @Override
  public <T extends AbstractPutObjectRequest> T addEncryption(final T request) {
    setEncryption(request::getMetadata, request::setMetadata);
    return request;
  }

  @Override
  public <T extends CopyObjectRequest> T addEncryption(final T request) {
    setEncryption(request::getNewObjectMetadata, request::setNewObjectMetadata);
    return request;
  }

  private void setEncryption(final Supplier<ObjectMetadata> getter, final Consumer<ObjectMetadata> setter) {
    ObjectMetadata objectMetadata = ofNullable(getter.get()).orElse(new ObjectMetadata());
    objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
    setter.accept(objectMetadata);
  }
}
