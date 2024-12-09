package org.sonatype.nexus.blobstore.api;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A unique identifier for a blob within a specific {@link BlobStore}.
 *
 * @since 3.0
 */
public class BlobId
  implements Serializable, Comparable<BlobId>
{
  private final String id;

  public BlobId(final String id) {
    this.id = checkNotNull(id);
  }

  public String asUniqueString() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BlobId blobId = (BlobId) o;

    return id.equals(blobId.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public int compareTo(final BlobId o) {
    return id.compareTo(o.id);
  }
}
