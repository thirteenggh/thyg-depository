package org.sonatype.nexus.blobstore.s3.internal;

import java.io.InputStream;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Uploads a file to S3.
 * @since 3.12
 */
public interface S3Uploader {

  /**
   * Upload a file to s3.
   */
  void upload(AmazonS3 s3, String bucket, String key, InputStream contents);
}
