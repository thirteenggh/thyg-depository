package org.sonatype.nexus.blobstore.s3.internal;

import java.io.IOException;
import java.util.Map;

import org.sonatype.nexus.blobstore.BlobAttributesSupport;
import org.sonatype.nexus.blobstore.api.BlobAttributes;
import org.sonatype.nexus.blobstore.api.BlobMetrics;

import com.amazonaws.services.s3.AmazonS3;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link BlobAttributes} backed by {@link S3PropertiesFile}
 *
 * @since 3.6.1
 */
public class S3BlobAttributes
    extends BlobAttributesSupport<S3PropertiesFile>
{
  public S3BlobAttributes(final AmazonS3 s3, final String bucket, final String key) {
    super(new S3PropertiesFile(s3, bucket, key), null, null);
  }

  public S3BlobAttributes(final AmazonS3 s3, final String bucket, final String key, final Map<String, String> headers,
                          final BlobMetrics metrics) {
    super(new S3PropertiesFile(s3, bucket, key), checkNotNull(headers), checkNotNull(metrics));
  }

  public boolean load() throws IOException {
    if (!propertiesFile.exists()) {
      return false;
    }
    propertiesFile.load();
    readFrom(propertiesFile);
    return true;
  }

  @Override
  public void store() throws IOException {
    writeTo(propertiesFile);
    propertiesFile.store();
  }
}
