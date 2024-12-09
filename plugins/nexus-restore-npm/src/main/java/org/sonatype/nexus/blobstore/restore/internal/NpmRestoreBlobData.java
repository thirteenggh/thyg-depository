package org.sonatype.nexus.blobstore.restore.internal;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;

/**
 * @since 3.6.1
 */
public final class NpmRestoreBlobData
    extends RestoreBlobDataSupport
{
  public enum NpmType
  {
    REPOSITORY_ROOT,
    PACKAGE_ROOT,
    TARBALL;
  }

  private final NpmType type;

  private final String packageId;

  private final String tarballName;

  NpmRestoreBlobData(final NpmType type,
                     final String packageId,
                     @Nullable final String tarballName,
                     final RestoreBlobData blobData)
  {
    super(blobData);
    this.type = type;
    this.packageId = packageId;
    this.tarballName = tarballName;
  }

  public NpmType getType() {
    return type;
  }

  public String getPackageId() {
    return packageId;
  }

  public String getTarballName() {
    return tarballName;
  }
}
