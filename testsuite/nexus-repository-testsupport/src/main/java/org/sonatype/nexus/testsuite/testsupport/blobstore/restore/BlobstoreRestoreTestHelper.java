package org.sonatype.nexus.testsuite.testsupport.blobstore.restore;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Repository;

/**
 * Helper class containing common functionality needed in ITs testing the restoration of component metadata from blobs.
 * Assumes a unit of work has already been started.
 */
public interface BlobstoreRestoreTestHelper
{
  String TYPE_ID = "blobstore.rebuildComponentDB";

  String BLOB_STORE_NAME_FIELD_ID = "blobstoreName";

  String RESTORE_BLOBS = "restoreBlobs";

  String UNDELETE_BLOBS = "undeleteBlobs";

  String INTEGRITY_CHECK = "integrityCheck";

  String DRY_RUN = "dryRun";

  void simulateComponentAndAssetMetadataLoss();

  void simulateAssetMetadataLoss();

  void simulateComponentMetadataLoss();

  void runRestoreMetadataTaskWithTimeout(long timeout, boolean dryRun);

  void runRestoreMetadataTask();

  void assertAssetMatchesBlob(Repository repository, String name);

  void assertAssetMatchesBlob(Repository repository, String... names);
}
