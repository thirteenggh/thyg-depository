package org.sonatype.nexus.blobstore.restore.pypi.internal;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.repository.pypi.PyPiRestoreUtil;

import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.aether.util.StringUtils.isEmpty;

/**
 * @since 3.16
 */
@Singleton
public class PyPiRestoreBlobDataFactory
{
  public PyPiRestoreBlobData create(@Nonnull final RestoreBlobData data) {
    checkState(!isEmpty(data.getBlobName()), "Blob name cannot be empty");

    String version = PyPiRestoreUtil.extractVersionFromPath(data.getBlobName());
    return new PyPiRestoreBlobData(data, version);
  }
}
