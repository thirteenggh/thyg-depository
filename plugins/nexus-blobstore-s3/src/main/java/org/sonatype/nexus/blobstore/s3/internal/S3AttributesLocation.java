package org.sonatype.nexus.blobstore.s3.internal;

import org.sonatype.nexus.blobstore.AttributesLocation;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Location for S3BlobStore attributes files
 *
 * @since 3.15
 */
public class S3AttributesLocation
    implements AttributesLocation
{
  private final String key;

  public S3AttributesLocation(final S3ObjectSummary summary) {
    checkNotNull(summary);
    this.key = summary.getKey();
  }

  @Override
  public String getFileName() {
    return key.substring(key.lastIndexOf('/') + 1);
  }

  @Override
  public String getFullPath() {
    return key;
  }
}
