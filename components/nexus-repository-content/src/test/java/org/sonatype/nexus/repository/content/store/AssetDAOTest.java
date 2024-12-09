package org.sonatype.nexus.repository.content.store;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.common.time.UTC;
import org.sonatype.nexus.datastore.api.DataSession;
import org.sonatype.nexus.datastore.api.DuplicateKeyException;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.store.example.TestAssetBlobDAO;
import org.sonatype.nexus.repository.content.store.example.TestAssetDAO;
import org.sonatype.nexus.repository.content.store.example.TestAssetData;
import org.sonatype.nexus.repository.content.store.example.TestComponentDAO;
import org.sonatype.nexus.repository.content.store.example.TestContentRepositoryDAO;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.sonatype.nexus.repository.content.store.ComponentDAOTest.browseComponents;
import static org.sonatype.nexus.repository.content.store.ComponentDAOTest.countComponents;

/**
 * Test {@link AssetDAO}.
 */
public class AssetDAOTest
    extends ExampleContentTestSupport
{
  private ContentRepositoryData contentRepository;

  private int repositoryId;

  @Before
  public void setupContent() {
    contentRepository = randomContentRepository();

    createContentRepository(contentRepository);

    repositoryId = contentRepository.repositoryId;

    generateRandomNamespaces(100);
    generateRandomNames(100);
    generateRandomVersions(100);
    generateRandomPaths(100);
  }

  private void createContentRepository(final ContentRepositoryData contentRepository) {
    try (DataSession<?> session = sessionRule.openSession("content")) {
      ContentRepositoryDAO dao = session.access(TestContentRepositoryDAO.class);
      dao.createContentRepository(contentRepository);
      session.getTransaction().commit();
    }
  }

  @Test
  public void testCrudOperations() throws InterruptedException {

    String aKind = "a kind";
    String anotherKind = "another kind";
    AssetData asset1 = randomAsset(repositoryId);
    AssetData asset2 = randomAsset(repositoryId);
    AssetData asset3 = randomAsset(repositoryId, aKind);
    AssetData asset4 = randomAsset(repositoryId, anotherKind);
    AssetData asset5 = randomAsset(repositoryId, anotherKind);

    // make sure paths are different
    asset2.setPath(asset1.path() + "/2");
    asset3.setPath(asset1.path() + "/3");
    asset4.setPath(asset1.path() + "/4");
    asset5.setPath(asset1.path() + "/5");

    String path1 = asset1.path();
    String path2 = asset2.path();

    Asset tempResult;

    // CREATE

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertThat(browseAssets(dao, repositoryId, null, 10, null), emptyIterable());

      dao.createAsset(asset1);

      assertThat(browseAssets(dao, repositoryId, null, 10, null), contains(
          allOf(samePath(asset1), sameKind(asset1), sameAttributes(asset1))));

      dao.createAsset(asset2);
      dao.createAsset(asset3);
      dao.createAsset(asset4);
      dao.createAsset(asset5);

      //browse all assets
      assertThat(browseAssets(dao, repositoryId, null, 10, null), contains(
          allOf(samePath(asset1), sameKind(asset1), sameAttributes(asset1)),
          allOf(samePath(asset2), sameKind(asset2), sameAttributes(asset2)),
          allOf(samePath(asset3), sameKind(asset3), sameAttributes(asset3)),
          allOf(samePath(asset4), sameKind(asset4), sameAttributes(asset4)),
          allOf(samePath(asset5), sameKind(asset5), sameAttributes(asset5))));

      //browse by kind
      assertThat(browseAssets(dao, repositoryId, aKind, 10, null), contains(
          allOf(samePath(asset3), sameKind(asset3), sameAttributes(asset3))));

      assertThat(browseAssets(dao, repositoryId, anotherKind, 10, null), contains(
          allOf(samePath(asset4), sameKind(asset4), sameAttributes(asset4)),
          allOf(samePath(asset5), sameKind(asset5), sameAttributes(asset5))));


      session.getTransaction().commit();
    }

    // TRY CREATE AGAIN

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      AssetData duplicate = new AssetData();
      duplicate.repositoryId = asset1.repositoryId;
      duplicate.setPath(asset1.path());
      duplicate.setKind(asset1.kind());
      duplicate.setAttributes(newAttributes("duplicate"));
      duplicate.setLastUpdated(OffsetDateTime.now());
      dao.createAsset(duplicate);

      session.getTransaction().commit();
      fail("Cannot create the same component twice");
    }
    catch (DuplicateKeyException e) {
      logger.debug("Got expected exception", e);
    }

    // READ

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertFalse(dao.readPath(repositoryId, "test-path").isPresent());

      tempResult = dao.readPath(repositoryId, path1).get();
      assertThat(tempResult, samePath(asset1));
      assertThat(tempResult, sameKind(asset1));
      assertThat(tempResult, sameAttributes(asset1));

      tempResult = dao.readPath(repositoryId, path2).get();
      assertThat(tempResult, samePath(asset2));
      assertThat(tempResult, sameKind(asset2));
      assertThat(tempResult, sameAttributes(asset2));
    }

    // UPDATE

    Thread.sleep(2); // NOSONAR make sure any new last updated times will be different

    // must use a new session as CURRENT_TIMESTAMP (used for last_updated) is fixed once used inside a session

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path1).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();

      asset1.attributes("custom-section-1").set("custom-key-1", "more-test-values-1");
      dao.updateAssetAttributes(asset1);
      asset1.setKind("new-kind-1");
      dao.updateAssetKind(asset1);

      tempResult = dao.readPath(repositoryId, path1).get();
      assertThat(tempResult, samePath(asset1));
      assertThat(tempResult, sameKind(asset1));
      assertThat(tempResult, sameAttributes(asset1));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated)); // should change as attributes have changed

      tempResult = dao.readPath(repositoryId, path2).get();

      oldCreated = tempResult.created();
      oldLastUpdated = tempResult.lastUpdated();

      asset2.assetId = null; // check a 'detached' entity with no internal id can be updated
      asset2.attributes("custom-section-2").set("custom-key-2", "more-test-values-2");
      dao.updateAssetAttributes(asset2);
      asset2.setKind("new-kind-2");
      dao.updateAssetKind(asset2);

      tempResult = dao.readPath(repositoryId, path2).get();
      assertThat(tempResult, samePath(asset2));
      assertThat(tempResult, sameKind(asset2));
      assertThat(tempResult, sameAttributes(asset2));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated)); // should change as attributes have changed

      session.getTransaction().commit();
    }

    // UPDATE AGAIN

    Thread.sleep(2); // NOSONAR make sure any new last updated times will be different

    // must use a new session as CURRENT_TIMESTAMP (used for last_updated) is fixed once used inside a session

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path1).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();

      asset1.attributes("custom-section-1").set("custom-key-1", "more-test-values-again");
      dao.updateAssetAttributes(asset1);

      tempResult = dao.readPath(repositoryId, path1).get();
      assertThat(tempResult, samePath(asset1));
      assertThat(tempResult, sameKind(asset1));
      assertThat(tempResult, sameAttributes(asset1));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated)); // should change as attributes changed again

      tempResult = dao.readPath(repositoryId, path2).get();

      oldCreated = tempResult.created();
      oldLastUpdated = tempResult.lastUpdated();

      dao.updateAssetAttributes(asset2);

      tempResult = dao.readPath(repositoryId, path2).get();
      assertThat(tempResult, samePath(asset2));
      assertThat(tempResult, sameKind(asset2));
      assertThat(tempResult, sameAttributes(asset2));
      assertThat(tempResult.created(), is(oldCreated));
      assertThat(tempResult.lastUpdated(), is(oldLastUpdated)); // won't have changed as attributes haven't changed

      session.getTransaction().commit();
    }

    // DELETE

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertTrue(dao.deleteAsset(asset1));

      assertThat(browseAssets(dao, repositoryId, null, 10, null), contains(
          allOf(samePath(asset2), sameKind(asset2), sameAttributes(asset2)),
          allOf(samePath(asset3), sameKind(asset3), sameAttributes(asset3)),
          allOf(samePath(asset4), sameKind(asset4), sameAttributes(asset4)),
          allOf(samePath(asset5), sameKind(asset5), sameAttributes(asset5))));

      assertTrue(dao.deleteAssets(repositoryId, 0));

      assertThat(browseAssets(dao, repositoryId, null, 10, null), emptyIterable());

      AssetData candidate = new AssetData();
      candidate.setRepositoryId(repositoryId);
      candidate.setPath("/test-path");
      assertFalse(dao.deleteAsset(candidate));
    }
  }

  @Test
  public void testLastDownloaded() throws InterruptedException {

    AssetData asset = randomAsset(repositoryId);
    String path = asset.path();
    Asset tempResult;

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);
      dao.createAsset(asset);
      session.getTransaction().commit();
    }

    // INITIAL DOWNLOAD

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      assertFalse(tempResult.lastDownloaded().isPresent());

      dao.markAsDownloaded(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertTrue(tempResult.lastDownloaded().isPresent());
      assertTrue(tempResult.lastDownloaded().get().isAfter(oldLastUpdated));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated));

      session.getTransaction().commit();
    }

    // SOME LATER DOWNLOAD

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      OffsetDateTime oldLastDownloaded = tempResult.lastDownloaded().get();

      dao.markAsDownloaded(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertTrue(tempResult.lastDownloaded().isPresent());
      assertTrue(tempResult.lastDownloaded().get().isAfter(oldLastDownloaded));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated));

      session.getTransaction().commit();
    }
  }

  @Test
  public void testAttachingBlobs() throws InterruptedException {

    AssetBlobData assetBlob1 = randomAssetBlob();
    AssetBlobData assetBlob2 = randomAssetBlob();
    AssetData asset = randomAsset(repositoryId);
    String path = asset.path();
    Asset tempResult;

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetBlobDAO dao = session.access(TestAssetBlobDAO.class);
      dao.createAssetBlob(assetBlob1);
      dao.createAssetBlob(assetBlob2);
      session.access(TestAssetDAO.class).createAsset(asset);
      session.getTransaction().commit();

      assertThat(dao.browseUnusedAssetBlobs(10, null),
          contains(sameBlob(assetBlob1), sameBlob(assetBlob2)));
    }

    // ATTACH BLOB

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      assertFalse(tempResult.blob().isPresent());

      asset.setAssetBlob(assetBlob1);
      dao.updateAssetBlobLink(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertTrue(tempResult.blob().isPresent());
      assertThat(tempResult.blob().get(), sameBlob(assetBlob1));
      assertThat(tempResult, sameBlob(asset));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated));

      session.getTransaction().commit();

      assertThat(session.access(TestAssetBlobDAO.class).browseUnusedAssetBlobs(10, null),
          contains(sameBlob(assetBlob2)));
    }

    // REPLACE BLOB

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      assertThat(tempResult.blob().get(), sameBlob(assetBlob1));

      asset.setAssetBlob(assetBlob2);
      dao.updateAssetBlobLink(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertTrue(tempResult.blob().isPresent());
      assertThat(tempResult.blob().get(), sameBlob(assetBlob2));
      assertThat(tempResult, sameBlob(asset));
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated));

      session.getTransaction().commit();

      assertThat(session.access(TestAssetBlobDAO.class).browseUnusedAssetBlobs(10, null),
          contains(sameBlob(assetBlob1)));
    }

    // REPLACING WITH SAME BLOB DOESN'T UPDATE

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      assertThat(tempResult.blob().get(), sameBlob(assetBlob2));

      asset.setAssetBlob(assetBlob2);
      dao.updateAssetBlobLink(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertTrue(tempResult.blob().isPresent());
      assertThat(tempResult.blob().get(), sameBlob(assetBlob2));
      assertThat(tempResult, sameBlob(asset));
      assertThat(tempResult.created(), is(oldCreated));
      assertThat(tempResult.lastUpdated(), is(oldLastUpdated));

      session.getTransaction().commit();

      assertThat(session.access(TestAssetBlobDAO.class).browseUnusedAssetBlobs(10, null),
          contains(sameBlob(assetBlob1)));
    }

    // DETACH BLOB

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      assertThat(tempResult.blob().get(), sameBlob(assetBlob2));

      asset.setAssetBlob(null);
      dao.updateAssetBlobLink(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertFalse(tempResult.blob().isPresent());
      assertThat(tempResult.created(), is(oldCreated));
      assertTrue(tempResult.lastUpdated().isAfter(oldLastUpdated));

      session.getTransaction().commit();

      assertThat(session.access(TestAssetBlobDAO.class).browseUnusedAssetBlobs(10, null),
          contains(sameBlob(assetBlob1), sameBlob(assetBlob2)));
    }

    // DETACHING BLOB AGAIN DOESN'T UPDATE

    Thread.sleep(2); // NOSONAR

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readPath(repositoryId, path).get();

      OffsetDateTime oldCreated = tempResult.created();
      OffsetDateTime oldLastUpdated = tempResult.lastUpdated();
      assertFalse(tempResult.blob().isPresent());

      asset.setAssetBlob(null);
      dao.updateAssetBlobLink(asset);

      tempResult = dao.readPath(repositoryId, path).get();
      assertFalse(tempResult.blob().isPresent());
      assertThat(tempResult.created(), is(oldCreated));
      assertThat(tempResult.lastUpdated(), is(oldLastUpdated));

      session.getTransaction().commit();

      assertThat(session.access(TestAssetBlobDAO.class).browseUnusedAssetBlobs(10, null),
          contains(sameBlob(assetBlob1), sameBlob(assetBlob2)));
    }
  }

  @Test
  public void testBrowseComponentAssets() {

    // scatter components and assets
    generateRandomRepositories(10);
    generateRandomContent(10, 100);

    List<Asset> browsedAssets = new ArrayList<>();

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO assetDao = session.access(TestAssetDAO.class);
      ComponentDAO componentDAO = session.access(TestComponentDAO.class);

      assertThat(generatedRepositories().stream()
          .map(ContentRepositoryData::contentRepositoryId)
          .collect(summingInt(r -> countAssets(assetDao, r))), is(100));

      assertThat(generatedRepositories().stream()
          .map(ContentRepositoryData::contentRepositoryId)
          .collect(summingInt(r -> countComponents(componentDAO, r))), is(10));

      // now gather them back by browsing
      generatedRepositories().forEach(r ->
          browseComponents(componentDAO, r.repositoryId, null, 10, null).stream()
              .map(ComponentData.class::cast)
              .map(assetDao::browseComponentAssets)
              .forEach(browsedAssets::addAll));
    }

    // we should have the same assets, but maybe in a different order
    // (use hamcrest class directly as javac picks the wrong static varargs method)
    assertThat(browsedAssets, new IsIterableContainingInAnyOrder<>(
        generatedAssets().stream()
            // ignore generated assets without components
            .filter(asset -> asset.component().isPresent())
            .map(ExampleContentTestSupport::samePath)
            .collect(toList())));

    // check assets under a 'detached' entity with no internal id can still be browsed
    ComponentData component = (ComponentData) generatedComponents().get(0);
    component.componentId = null;
    try (DataSession<?> session = sessionRule.openSession("content")) {
      assertTrue(session.access(TestAssetDAO.class).browseComponentAssets(component).stream()
          .map(Asset::component)
          .map(Optional::get)
          .allMatch(sameCoordinates(component)::matches));
    }
  }

  @Test
  public void testContinuationBrowsing() {

    generateRandomNamespaces(1);
    generateRandomNames(1);
    generateRandomVersions(1);
    generateRandomPaths(10000);
    generateRandomRepositories(1);
    generateRandomContent(1, 1000);

    repositoryId = generatedRepositories().get(0).repositoryId;

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertThat(countAssets(dao, repositoryId), is(1000));

      int page = 0;

      Continuation<Asset> assets = browseAssets(dao, repositoryId, null, 10, null);
      while (!assets.isEmpty()) {

        // verify we got the expected slice
        assertThat(assets, new IsIterableContainingInOrder<>(
            generatedAssets()
                .subList(page * 10, (page + 1) * 10)
                .stream()
                .map(ExampleContentTestSupport::samePath)
                .collect(toList())));

        assets = browseAssets(dao, repositoryId, null, 10, assets.nextContinuationToken());

        page++;
      }

      assertThat(page, is(100));
    }
  }

  @Test
  public void testFlaggedBrowsing() {

    TestAssetData asset1 = randomAsset(repositoryId);
    TestAssetData asset2 = randomAsset(repositoryId);
    asset2.setPath(asset1.path() + "/2"); // make sure paths are different

    try (DataSession<?> session = sessionRule.openSession("content")) {
      TestAssetDAO dao = session.access(TestAssetDAO.class);

      // our bespoke schema will be applied automatically via 'extendSchema'...

      dao.createAsset(asset1);
      dao.createAsset(asset2);

      assertThat(dao.browseFlaggedAssets(repositoryId, 10, null), emptyIterable());

      asset2.setTestFlag(true);
      dao.updateAssetFlag(asset2);

      assertThat(dao.browseFlaggedAssets(repositoryId, 10, null),
          contains(allOf(samePath(asset2), sameAttributes(asset2))));

      asset1.setTestFlag(true);
      dao.updateAssetFlag(asset1);

      asset2.setTestFlag(false);
      dao.updateAssetFlag(asset2);

      assertThat(dao.browseFlaggedAssets(repositoryId, 10, null),
          contains(allOf(samePath(asset1), sameAttributes(asset1))));
    }
  }

  @Test
  public void testReadPathTest() {
    TestAssetData asset1 = randomAsset(repositoryId);
    TestAssetData asset2 = randomAsset(repositoryId);
    asset2.setPath(asset1.path() + "/2"); // make sure paths are different

    try (DataSession<?> session = sessionRule.openSession("content")) {
      TestAssetDAO dao = session.access(TestAssetDAO.class);

      // our bespoke schema will be applied automatically via 'extendSchema'...

      dao.createAsset(asset1);
      dao.createAsset(asset2);

      asset2.setTestFlag(true);
      dao.updateAssetFlag(asset2);

      TestAssetData test1 = dao.readPathTest(repositoryId, asset1.path()).orElse(null);
      TestAssetData test2 = dao.readPathTest(repositoryId, asset2.path()).orElse(null);
      assertThat(test1, notNullValue());
      assertThat(test1.getTestFlag(), equalTo(false));
      assertThat(test2, notNullValue());
      assertThat(test2.getTestFlag(), equalTo(true));

      Continuation<Asset> continuation = dao.browseFlaggedAssets(repositoryId, 10, null);
      assertThat(continuation.size(), equalTo(1));
      TestAssetData test3 = continuation.stream()
          .filter(obj -> obj instanceof TestAssetData)
          .map(obj -> (TestAssetData) obj)
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Expect asset not found"));
      assertThat(test3.getTestFlag(), equalTo(true));
    }
  }

  @Test
  public void testDeleteAllAssets() {

    // scatter components and assets
    generateRandomRepositories(1);
    generateRandomContent(100, 100);

    repositoryId = generatedRepositories().get(0).contentRepositoryId();

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertThat(countAssets(dao, repositoryId), is(100));

      assertThat(browseAssets(dao, repositoryId, null, 100, null).size(), is(100));

      dao.deleteAssets(repositoryId, 20);

      assertThat(browseAssets(dao, repositoryId, null, 100, null).size(), is(80));

      dao.deleteAssets(repositoryId, 10);

      assertThat(browseAssets(dao, repositoryId, null, 100, null).size(), is(70));

      dao.deleteAssets(repositoryId, 0);

      assertThat(browseAssets(dao, repositoryId, null, 100, null).size(), is(0));

      dao.deleteAssets(repositoryId, -1);

      assertThat(browseAssets(dao, repositoryId, null, 100, null).size(), is(0));
    }
  }

  @Test
  public void testDeletePaths() {

    AssetData asset1 = randomAsset(repositoryId);
    AssetData asset2 = randomAsset(repositoryId);
    AssetData asset3 = randomAsset(repositoryId);
    AssetData asset4 = randomAsset(repositoryId);
    AssetData asset5 = randomAsset(repositoryId);

    // make sure paths are different
    asset2.setPath(asset1.path() + "/2");
    asset3.setPath(asset1.path() + "/3");
    asset4.setPath(asset1.path() + "/4");
    asset5.setPath(asset1.path() + "/5");

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      dao.createAsset(asset1);
      dao.createAsset(asset2);
      dao.createAsset(asset3);
      dao.createAsset(asset4);
      dao.createAsset(asset5);

      assertThat(countAssets(dao, repositoryId), is(5));

      dao.deleteAssetsByPaths(repositoryId, asList(asset1.path(), asset2.path(), asset3.path()));

      assertThat(browseAssets(dao, repositoryId, null, 5, null).size(), is(2));

      dao.deleteAssetsByPaths(repositoryId, asList(asset4.path(), asset5.path()));

      assertThat(browseAssets(dao, repositoryId, null, 5, null).size(), is(0));
    }
  }

  @Test
  public void testPurgeOperation() {
    AssetData asset1 = randomAsset(repositoryId);
    AssetData asset2 = randomAsset(repositoryId);
    asset2.setPath(asset1.path() + "/2"); // make sure paths are different

    asset1.setLastDownloaded(UTC.now().minusDays(2));
    asset2.setLastDownloaded(UTC.now().minusDays(4));

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);
      dao.createAsset(asset1);
      dao.createAsset(asset2);
      session.getTransaction().commit();
    }

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertTrue(dao.readPath(repositoryId, asset1.path()).isPresent());
      assertTrue(dao.readPath(repositoryId, asset2.path()).isPresent());

      int[] assetIds = dao.selectNotRecentlyDownloaded(repositoryId, 3, 10);
      assertThat(assetIds, is(new int[]{2}));

      if ("H2".equals(session.sqlDialect())) {
        dao.purgeSelectedAssets(stream(assetIds).boxed().toArray(Integer[]::new));
      }
      else {
        dao.purgeSelectedAssets(assetIds);
      }

      assertTrue(dao.readPath(repositoryId, asset1.path()).isPresent());
      assertFalse(dao.readPath(repositoryId, asset2.path()).isPresent());
    }
  }

  @Test
  public void testRoundTrip() {
    AssetData asset1 = randomAsset(repositoryId);
    AssetData asset2 = randomAsset(repositoryId);
    asset2.setPath(asset1.path() + "/2"); // make sure paths are different

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);
      dao.createAsset(asset1);
      dao.createAsset(asset2);
      session.getTransaction().commit();
    }

    Asset tempResult;

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      tempResult = dao.readAsset(asset1.assetId).get();
      assertThat(tempResult, samePath(asset1));
      assertThat(tempResult, sameKind(asset1));
      assertThat(tempResult, sameAttributes(asset1));

      tempResult = dao.readAsset(asset2.assetId).get();
      assertThat(tempResult, samePath(asset2));
      assertThat(tempResult, sameKind(asset2));
      assertThat(tempResult, sameAttributes(asset2));
    }
  }

  @Test
  public void testBrowseAssetsInRepositories() {
    ContentRepositoryData anotherContentRepository = randomContentRepository();
    createContentRepository(anotherContentRepository);
    int anotherRepositoryId = anotherContentRepository.repositoryId;
    AssetData asset1 = randomAsset(repositoryId);
    AssetData asset2 = randomAsset(repositoryId);
    AssetData asset3 = randomAsset(anotherRepositoryId);
    AssetData asset4 = randomAsset(anotherRepositoryId);

    // make sure paths are different
    asset2.setPath(asset1.path() + "/2");
    asset3.setPath(asset1.path() + "/3");
    asset4.setPath(asset1.path() + "/4");

    // CREATE

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);

      assertThat(
          dao.browseAssetsInRepositories(newHashSet(repositoryId, anotherRepositoryId), 10, null),
          emptyIterable());

      dao.createAsset(asset1);

      assertThat(
          dao.browseAssetsInRepositories(newHashSet(repositoryId, anotherRepositoryId), 10, null),
          contains(allOf(samePath(asset1), sameAttributes(asset1))));

      dao.createAsset(asset2);
      dao.createAsset(asset3);
      dao.createAsset(asset4);

      //browse all assets
      assertThat(
          dao.browseAssetsInRepositories(newHashSet(repositoryId, anotherRepositoryId), 10, null),
          contains(allOf(samePath(asset1), sameAttributes(asset1)), allOf(samePath(asset2), sameAttributes(asset2)),
              allOf(samePath(asset3), sameAttributes(asset3)), allOf(samePath(asset4), sameAttributes(asset4))));

      session.getTransaction().commit();
    }
  }

  @Test
  public void testSetLastDownloaded() {
    AssetData asset1 = randomAsset(repositoryId);

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);
      dao.createAsset(asset1);

      OffsetDateTime dateTime = OffsetDateTime.now(ZoneOffset.UTC).minusDays(1);
      dao.lastDownloaded(asset1.assetId, dateTime);

      assertThat(dao.readAsset(asset1.assetId).get().lastDownloaded().orElse(null), is(dateTime));
    }
  }

  @Test
  public void testLastUpdated() {
    AssetData asset1 = randomAsset(repositoryId);

    try (DataSession<?> session = sessionRule.openSession("content")) {
      AssetDAO dao = session.access(TestAssetDAO.class);
      dao.createAsset(asset1);

      OffsetDateTime dateTime = OffsetDateTime.now(ZoneOffset.UTC).minusDays(1);
      dao.lastUpdated(asset1.assetId, dateTime);

      assertThat(dao.readAsset(asset1.assetId).get().lastUpdated(), is(dateTime));
    }
  }

  static int countAssets(final AssetDAO dao, final int repositoryId) {
    return dao.countAssets(repositoryId, null, null, null);
  }

  static Continuation<Asset> browseAssets(final AssetDAO dao,
                                          final int repositoryId,
                                          final String kind,
                                          final int limit,
                                          final String continuationToken)
  {
    return dao.browseAssets(repositoryId, limit, continuationToken, kind, null, null);
  }
}
