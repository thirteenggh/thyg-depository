package org.sonatype.nexus.blobstore.s3.internal;

import java.io.InputStream;

import javax.inject.Named;

import org.sonatype.nexus.blobstore.api.BlobStoreException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

/**
 * Uploads a file with the TransferManager.
 * @since 3.7
 * @deprecated replaced with {@link MultipartUploader}
 */
@Deprecated
@Named("transfer-manager-uploader")
public class TransferManagerUploader
    implements S3Uploader
{

  public void upload(final AmazonS3 s3, final String bucket, final String key, final InputStream contents) {
    try {
      TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3).build();
      transferManager.upload(bucket, key, contents, new ObjectMetadata())
          .waitForCompletion();
    } catch (InterruptedException e) {
      throw new BlobStoreException("error uploading blob", e, null);
    }
  }
}
