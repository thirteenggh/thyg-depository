package org.sonatype.nexus.repository.content.store;

import java.time.OffsetDateTime;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.time.UTC;
import org.sonatype.nexus.datastore.api.DataSession;
import org.sonatype.nexus.datastore.api.DuplicateKeyException;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.content.store.example.TestAssetBlobDAO;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.sonatype.nexus.testcommon.matchers.NexusMatchers.time;

/**
 * Test {@link AssetBlobDAO}.
 */
public class AssetBlobDAOTest
    extends ExampleContentTestSupport
{
  @Test
  public void testCrudOperations() {

    AssetBlobData assetBlob1 = randomAssetBlob();
    AssetBlobData assetBlob2 = randomAssetBlob();

    BlobRef blobRef1 = assetBlob1.blobRef();
    BlobRef blobRef2 = assetBlob2.blobRef();

    AssetBlob tempResult;

    // CREATE

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);

      assertThat(dao.browseUnusedAssetBlobs(1, null), emptyIterable());

      dao.createAssetBlob(assetBlob1);

      assertThat(dao.browseUnusedAssetBlobs(1, null), contains(sameBlob(assetBlob1)));

      dao.createAssetBlob(assetBlob2);

      assertThat(dao.browseUnusedAssetBlobs(1, null), contains(sameBlob(assetBlob1)));

      assertThat(dao.browseUnusedAssetBlobs(2, null), contains(sameBlob(assetBlob1), sameBlob(assetBlob2)));

      session.getTransaction().commit();
    }

    // TRY CREATE AGAIN

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);

      AssetBlobData duplicate = new AssetBlobData();
      duplicate.setBlobRef(assetBlob1.blobRef());
      duplicate.setBlobSize(1234);
      duplicate.setContentType("text/html");
      duplicate.setChecksums(ImmutableMap.of());
      duplicate.setBlobCreated(UTC.now());
      dao.createAssetBlob(duplicate);

      session.getTransaction().commit();
      fail("Cannot create the same component twice");
    }
    catch (DuplicateKeyException e) {
      logger.debug("Got expected exception", e);
    }

    // READ

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);

      assertFalse(dao.readAssetBlob(new BlobRef("test-node", "test-store", "test-blob")).isPresent());

      tempResult = dao.readAssetBlob(blobRef1).get();
      assertThat(tempResult, sameBlob(assetBlob1));

      tempResult = dao.readAssetBlob(blobRef2).get();
      assertThat(tempResult, sameBlob(assetBlob2));
    }

    // NO UPDATE METHODS (each blob is considered immutable)

    // DELETE

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);

      assertTrue(dao.deleteAssetBlob(blobRef1));

      assertThat(dao.browseUnusedAssetBlobs(1, null), contains(sameBlob(assetBlob2)));

      assertTrue(dao.deleteAssetBlob(blobRef2));

      assertThat(dao.browseUnusedAssetBlobs(1, null), emptyIterable());

      assertFalse(dao.deleteAssetBlob(new BlobRef("test-node", "test-store", "test-blob")));
    }
  }

  @Test
  public void testBrowseAll() {
    AssetBlobData assetBlob1 = randomAssetBlob();
    AssetBlobData assetBlob2 = randomAssetBlob();

    BlobRef blobRef1 = assetBlob1.blobRef();

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);

      dao.createAssetBlob(assetBlob1);

      assertThat(dao.browseAssetBlobs(1, null), contains(sameBlob(assetBlob1)));

      dao.createAssetBlob(assetBlob2);

      assertThat(dao.browseAssetBlobs(2, null), contains(sameBlob(assetBlob1), sameBlob(assetBlob2)));

      dao.deleteAssetBlob(blobRef1);

      assertThat(dao.browseAssetBlobs(2, null), contains(sameBlob(assetBlob2)));
    }
  }

  @Test
  public void testBlob() {
    AssetBlobData assetBlob1 = randomAssetBlob();
    BlobRef blobRef1 = assetBlob1.blobRef();

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);
      dao.createAssetBlob(assetBlob1);

      OffsetDateTime blobCreated = OffsetDateTime.now().minusDays(1);
      dao.setBlobCreated(blobRef1, blobCreated);

      assertThat(dao.readAssetBlob(blobRef1).get().blobCreated(), time(blobCreated));
    }
  }
}
