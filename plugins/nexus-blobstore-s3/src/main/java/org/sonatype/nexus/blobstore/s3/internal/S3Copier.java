package org.sonatype.nexus.blobstore.s3.internal;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Copies a file in S3.
 * @since 3.15
 */
public interface S3Copier {

  /**
   * Copies a file in s3.
   */
  void copy(AmazonS3 s3, String bucket, String sourcePath, String destinationPath);
}
