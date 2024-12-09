package org.sonatype.nexus.blobstore.s3.internal.encryption;

import com.amazonaws.services.s3.model.AbstractPutObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;

/**
 * Adds any encryption necessary to S3 requests.
 *
 * @since 3.19
 */
public interface S3Encrypter
{
  <T extends InitiateMultipartUploadRequest> T addEncryption(T request);
  <T extends AbstractPutObjectRequest> T addEncryption(T request);
  <T extends CopyObjectRequest> T addEncryption(T request);
}
