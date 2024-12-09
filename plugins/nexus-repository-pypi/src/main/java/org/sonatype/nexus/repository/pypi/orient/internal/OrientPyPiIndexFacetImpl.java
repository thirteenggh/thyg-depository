package org.sonatype.nexus.repository.pypi.orient.internal;

import javax.annotation.Priority;
import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.pypi.internal.PyPiIndexFacet;
import org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalDeleteBlob;
import org.sonatype.nexus.transaction.UnitOfWork;

import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.INDEX_PATH_PREFIX;
import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.normalizeName;
import static org.sonatype.nexus.repository.pypi.orient.internal.OrientPyPiDataUtils.findAsset;

/**
 * {@link PyPiIndexFacet} implementation.
 *
 * @since 3.16
 */
@Named
@Priority(Integer.MAX_VALUE)
public class OrientPyPiIndexFacetImpl
    extends FacetSupport
    implements PyPiIndexFacet
{
  @TransactionalDeleteBlob
  public void deleteIndex(final String packageName)
  {
    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());

    String indexPath = PyPiPathUtils.indexPath(normalizeName(packageName));
    Asset cachedIndex = findAsset(tx, bucket, indexPath);

    /*
      There is a chance that the index wasn't found because of package name normalization. For example '.'
      characters are normalized to '-' so jts.python would have an index at /simple/jts-python/. It is possible that
      we could just check for the normalized name but we check for both just in case. Searching for an index with a
      normalized name first means that most, if not all, index deletions will only perform a single search.

      See https://issues.sonatype.org/browse/NEXUS-19303 for additional context.
     */
    if (cachedIndex == null) {
      indexPath = PyPiPathUtils.indexPath(packageName);
      cachedIndex = findAsset(tx, bucket, indexPath);
    }

    if (cachedIndex != null) {
      tx.deleteAsset(cachedIndex);
    }
  }

  @TransactionalDeleteBlob
  public void deleteRootIndex() {
    StorageTx tx = UnitOfWork.currentTx();
    Bucket bucket = tx.findBucket(getRepository());
    Asset rootIndex = findAsset(tx, bucket, INDEX_PATH_PREFIX);
    if (rootIndex != null) {
      tx.deleteAsset(rootIndex);
    }
  }
}
