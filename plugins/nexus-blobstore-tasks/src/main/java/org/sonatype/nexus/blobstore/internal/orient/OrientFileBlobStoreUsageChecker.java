package org.sonatype.nexus.blobstore.internal.orient;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobId;
import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreUsageChecker;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.repository.storage.ComponentDatabase;

import com.codahale.metrics.annotation.Timed;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

/**
 * Check if a blob is reference in the corresponding metadata.
 *
 * @since 3.6
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE)
@FeatureFlag(name = "nexus.orient.store.content")
public class OrientFileBlobStoreUsageChecker
    implements BlobStoreUsageChecker
{
  private static final String ANY_NODE = "%";

  private final OSQLSynchQuery<ODocument> assetBlobRefQuery = new OSQLSynchQuery<>(
      "SELECT FROM asset WHERE name = ? AND blob_ref LIKE ? LIMIT 1"
  );

  private final Supplier<ODatabaseDocumentTx> txSupplier;

  @Inject
  public OrientFileBlobStoreUsageChecker(@Named(ComponentDatabase.NAME) final Provider<DatabaseInstance> databaseInstanceProvider)
  {
    this.txSupplier = () -> databaseInstanceProvider.get().acquire();
  }

  @Override
  @Timed
  public boolean test(final BlobStore blobStore, final BlobId blobId, final String blobName) {
    BlobRef blobRef = new BlobRef(ANY_NODE, blobStore.getBlobStoreConfiguration().getName(), blobId.asUniqueString());
    try (ODatabaseDocumentTx tx = txSupplier.get()) {
      tx.begin();
      List<ODocument> results = tx.command(assetBlobRefQuery).execute(blobName, blobRef.toString());
      return !results.isEmpty();
    }
  }
}
