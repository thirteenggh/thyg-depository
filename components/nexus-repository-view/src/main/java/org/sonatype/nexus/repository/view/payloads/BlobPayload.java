package org.sonatype.nexus.repository.view.payloads;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.repository.view.Payload;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Blob} backed payload.
 *
 * @since 3.0
 */
public class BlobPayload
    implements Payload
{
  private final Blob blob;

  private final String contentType;

  public BlobPayload(final Blob blob, @Nullable final String contentType) {
    this.blob = checkNotNull(blob);
    this.contentType = contentType;
  }

  @Override
  public InputStream openInputStream() throws IOException {
    return blob.getInputStream();
  }

  @Override
  public long getSize() {
    return blob.getMetrics().getContentSize();
  }

  @Nullable
  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "blob=" + blob +
        ", contentType='" + contentType + '\'' +
        '}';
  }
}
