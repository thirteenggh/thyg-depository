package org.sonatype.nexus.blobstore.group.internal;

import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.group.BlobStoreGroup;
import org.sonatype.nexus.blobstore.group.FillPolicy;

/**
 * {@link FillPolicy} that writes to first blobstore in group.
 *
 * @since 3.14
 */
@Named(WriteToFirstMemberFillPolicy.TYPE)
public class WriteToFirstMemberFillPolicy
    extends ComponentSupport
    implements FillPolicy
{

  public static final String TYPE = "writeToFirst";
  private static final String NAME = "Write to First";

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  @Nullable
  public BlobStore chooseBlobStore(final BlobStoreGroup blobStoreGroup, final Map<String, String> headers) {
    return blobStoreGroup
        .getMembers().stream()
        .filter(BlobStore::isWritable)
        .filter(BlobStore::isStorageAvailable)
        .findFirst()
        .orElse(null);
  }
}
