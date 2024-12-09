package org.sonatype.nexus.repository.apt.internal.snapshot;

import org.sonatype.nexus.repository.view.Content;

import static org.sonatype.nexus.repository.apt.internal.AptMimeTypes.BZIP;
import static org.sonatype.nexus.repository.apt.internal.AptMimeTypes.GZIP;
import static org.sonatype.nexus.repository.apt.internal.AptMimeTypes.SIGNATURE;
import static org.sonatype.nexus.repository.apt.internal.AptMimeTypes.TEXT;
import static org.sonatype.nexus.repository.apt.internal.AptMimeTypes.XZ;

/**
 * @since 3.17
 */
public class SnapshotItem
{
  public static enum Role
  {
    RELEASE_INDEX(TEXT),
    RELEASE_INLINE_INDEX(TEXT),
    PACKAGE_INDEX_RAW(TEXT),
    RELEASE_SIG(SIGNATURE),
    PACKAGE_INDEX_GZ(GZIP),
    PACKAGE_INDEX_BZ2(BZIP),
    PACKAGE_INDEX_XZ(XZ);

    private final String mimeType;

    Role(final String mimeType) {
      this.mimeType = mimeType;
    }

    public String getMimeType() {
      return mimeType;
    }
  }

  public static class ContentSpecifier
  {
    public final String path;

    public final Role role;

    public ContentSpecifier(final String path, final Role role) {
      super();
      this.path = path;
      this.role = role;
    }
  }

  public final ContentSpecifier specifier;

  public final Content content;

  public SnapshotItem(final ContentSpecifier specifier, final Content content) {
    super();
    this.specifier = specifier;
    this.content = content;
  }
}
