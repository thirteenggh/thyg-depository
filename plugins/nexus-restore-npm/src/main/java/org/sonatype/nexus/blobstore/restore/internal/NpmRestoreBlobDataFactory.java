package org.sonatype.nexus.blobstore.restore.internal;

import javax.annotation.Nonnull;

import org.sonatype.nexus.blobstore.restore.internal.NpmRestoreBlobData.NpmType;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;

import static com.google.common.base.Preconditions.checkState;
import static org.sonatype.nexus.blobstore.restore.internal.NpmRestoreBlobData.NpmType.PACKAGE_ROOT;
import static org.sonatype.nexus.blobstore.restore.internal.NpmRestoreBlobData.NpmType.REPOSITORY_ROOT;
import static org.sonatype.nexus.blobstore.restore.internal.NpmRestoreBlobData.NpmType.TARBALL;
import static org.eclipse.aether.util.StringUtils.isEmpty;

/**
 * @since 3.6.1
 */
public final class NpmRestoreBlobDataFactory
{
  private NpmRestoreBlobDataFactory() {}

  /**
   * Creates a {@link NpmRestoreBlobData} object. NPM asset types are identified by their name:
   * - {@link REPOSITORY_ROOT} will have a name of '-/all'
   * - {@link TARBALL} will contain a '/-/' followed by the tarball name
   * - {@link PACKAGE_ROOT} will have neither of the above characteristics
   */
  public static NpmRestoreBlobData create(@Nonnull final RestoreBlobData data) {
    checkState(!isEmpty(data.getBlobName()), "Blob name cannot be empty");

    String[] parts = data.getBlobName().split("/-/");

    if (parts.length == 1) {
      String id = parts[0];
      NpmType type = "-/all".equals(id) ? REPOSITORY_ROOT : PACKAGE_ROOT;

      return new NpmRestoreBlobData(type, id, null, data);
    }

    return new NpmRestoreBlobData(TARBALL, parts[0], parts[1], data);
  }
}
