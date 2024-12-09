package org.sonatype.nexus.blobstore.s3.internal.encryption;

import javax.inject.Named;

import com.amazonaws.services.s3.model.AbstractPutObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;

/**
 * Adds no encryption to a S3 request.
 *
 * @since 3.19
 */
@Named(NoEncrypter.ID)
public class NoEncrypter
    implements S3Encrypter
{
  public static final String ID = "none";
  public static final String NAME = "None";

  public static final NoEncrypter INSTANCE = new NoEncrypter();

  @Override
  public <T extends InitiateMultipartUploadRequest> T addEncryption(final T request) {
    return request;
  }

  @Override
  public <T extends AbstractPutObjectRequest> T addEncryption(final T request) {
    return request;
  }

  @Override
  public <T extends CopyObjectRequest> T addEncryption(final T request) {
    return request;
  }
}
