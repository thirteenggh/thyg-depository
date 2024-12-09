package org.sonatype.nexus.repository.storage;

import java.util.Map;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.common.node.NodeAccess;

import com.google.common.hash.HashCode;

/**
 * Marker class for a pre-fetched asset blob
 * 
 * @since 3.13
 */
public class PrefetchedAssetBlob
  extends AssetBlob
{
  public PrefetchedAssetBlob(final NodeAccess nodeAccess,
                             final BlobStore blobStore,
                             final Blob blob,
                             final String contentType,
                             final Map<HashAlgorithm, HashCode> hashes,
                             final boolean hashesVerified)
  {
    super(nodeAccess, blobStore, store -> blob, contentType, hashes, hashesVerified);
    /*
     * Pre-fetched blobs are stored in the blob-store but when a duplicate is encountered it is not used meaning it needs
     * to be cleaned up. Because it hasn't been ingested the cleanup fails on BlobTx.commit, this method ingests the blob
     * to ensure that it is correctly deleted. We use this class so that we don't eagerly ingest ALL blobs.
     */
    getBlob();
  }
}
