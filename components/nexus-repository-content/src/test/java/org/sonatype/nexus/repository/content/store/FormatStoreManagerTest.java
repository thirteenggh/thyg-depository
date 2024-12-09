package org.sonatype.nexus.repository.content.store;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityUUID;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.time.UTC;
import org.sonatype.nexus.content.testsuite.groups.SQLTestGroup;
import org.sonatype.nexus.datastore.api.ContentDataAccess;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AttributeChangeSet;
import org.sonatype.nexus.repository.content.event.asset.AssetAttributesEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetCreatedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetDeletedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetDownloadedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetKindEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetPreDeleteEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetUpdatedEvent;
import org.sonatype.nexus.repository.content.event.component.ComponentAttributesEvent;
import org.sonatype.nexus.repository.content.event.component.ComponentCreatedEvent;
import org.sonatype.nexus.repository.content.event.component.ComponentDeletedEvent;
import org.sonatype.nexus.repository.content.event.component.ComponentKindEvent;
import org.sonatype.nexus.repository.content.event.component.ComponentPreDeleteEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryCreatedEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryDeletedEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryPreDeleteEvent;
import org.sonatype.nexus.repository.content.facet.ContentFacetFinder;
import org.sonatype.nexus.repository.content.store.example.TestAssetBlobDAO;
import org.sonatype.nexus.repository.content.store.example.TestAssetDAO;
import org.sonatype.nexus.repository.content.store.example.TestAssetData;
import org.sonatype.nexus.repository.content.store.example.TestAssetStore;
import org.sonatype.nexus.repository.content.store.example.TestBespokeStoreModule;
import org.sonatype.nexus.repository.content.store.example.TestComponentDAO;
import org.sonatype.nexus.repository.content.store.example.TestContentRepositoryDAO;
import org.sonatype.nexus.repository.content.store.example.TestPlainStoreModule;
import org.sonatype.nexus.testdb.DataSessionRule;
import org.sonatype.nexus.transaction.TransactionModule;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.UnitOfWork;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.eclipse.sisu.wire.WireModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.datastore.mybatis.CombUUID.combUUID;
import static org.sonatype.nexus.repository.content.AttributeOperation.SET;

/**
 * Test {@link FormatStoreManager}.
 */
@Category(SQLTestGroup.class)
public class FormatStoreManagerTest
    extends TestSupport
{
  @Rule
  public DataSessionRule sessionRule = new DataSessionRule("content")
      .handle(new BlobRefTypeHandler())
      .access(TestContentRepositoryDAO.class)
      .access(TestComponentDAO.class)
      .access(TestAssetBlobDAO.class)
      .access(TestAssetDAO.class);

  @Mock
  Repository repository;

  @Mock
  ContentFacetFinder contentFacetFinder;

  @Mock
  EventManager eventManager;

  class SessionModule
      extends AbstractModule
  {
    @Override
    protected void configure() {
      bind(DataSessionSupplier.class).toInstance(sessionRule);
      bind(ContentFacetFinder.class).toInstance(contentFacetFinder);
      bind(EventManager.class).toInstance(eventManager);
    }
  }

  @Before
  public void setUp() {
    when(contentFacetFinder.findRepository(eq("test"), anyInt())).thenReturn(Optional.of(repository));
  }

  @Test
  public void testPlainBindings() {
    Injector injector =
        Guice.createInjector(new WireModule(new TestPlainStoreModule(), new SessionModule(), new TransactionModule()));

    FormatStoreManager underTest = injector.getInstance(Key.get(FormatStoreManager.class, Names.named("test")));

    ContentRepositoryStore<?> contentRepositoryStore = underTest.contentRepositoryStore("content");
    ComponentStore<?> componentStore = underTest.componentStore("content");
    AssetStore<?> assetStore = underTest.assetStore("content");
    AssetBlobStore<?> assetBlobStore = underTest.assetBlobStore("content");

    // check the appropriate DAOs have been bound
    assertDaoBinding(contentRepositoryStore, TestContentRepositoryDAO.class);
    assertDaoBinding(componentStore, TestComponentDAO.class);
    assertDaoBinding(assetStore, TestAssetDAO.class);
    assertDaoBinding(assetBlobStore, TestAssetBlobDAO.class);

    // check that previously requested stores are cached to keep creation costs down
    assertThat(underTest.contentRepositoryStore("content"), sameInstance(contentRepositoryStore));
    assertThat(underTest.componentStore("content"), sameInstance(componentStore));
    assertThat(underTest.assetStore("content"), sameInstance(assetStore));
    assertThat(underTest.assetBlobStore("content"), sameInstance(assetBlobStore));
  }

  @Test
  public void testPlainOperations() {
    Injector injector =
        Guice.createInjector(new WireModule(new TestPlainStoreModule(), new SessionModule(), new TransactionModule()));

    FormatStoreManager underTest = injector.getInstance(Key.get(FormatStoreManager.class, Names.named("test")));

    ContentRepositoryStore<?> contentRepositoryStore = underTest.contentRepositoryStore("content");
    ComponentStore<?> componentStore = underTest.componentStore("content");
    AssetStore<?> assetStore = underTest.assetStore("content");
    AssetBlobStore<?> assetBlobStore = underTest.assetBlobStore("content");

    ContentRepositoryData repository = new ContentRepositoryData();
    repository.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    repository.setConfigRepositoryId(new EntityUUID(combUUID()));
    contentRepositoryStore.createContentRepository(repository);

    ComponentData component = new ComponentData();
    component.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    component.setRepositoryId(repository.repositoryId);
    component.setNamespace("");
    component.setName("testComponent");
    component.setKind("aKind");
    component.setVersion("1.0");
    componentStore.createComponent(component);

    AssetData asset = new AssetData();
    asset.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    asset.setRepositoryId(repository.repositoryId);
    asset.setComponent(component);
    asset.setPath("/path/to/asset");
    asset.setKind("test");
    assetStore.createAsset(asset);

    AssetBlobData assetBlob = new AssetBlobData();
    assetBlob.setBlobRef(new BlobRef("local", "default", "testBlob"));
    assetBlob.setBlobSize(0);
    assetBlob.setContentType("text/plain");
    assetBlob.setChecksums(ImmutableMap.of());
    assetBlob.setBlobCreated(UTC.now());
    assetBlobStore.createAssetBlob(assetBlob);

    asset.setAssetBlob(assetBlob);
    assetStore.updateAssetBlobLink(asset);

    Optional<Asset> result = assetStore.readPath(repository.repositoryId, "/path/to/asset");
    assertThat(result.get().component().get().name(), is("testComponent"));
    assertThat(result.get().blob().get().blobRef().getBlob(), is("testBlob"));
  }

  @Test
  public void testBespokeBindings() {
    Injector injector =
        Guice.createInjector(new WireModule(new TestBespokeStoreModule(), new SessionModule(), new TransactionModule()));

    FormatStoreManager underTest = injector.getInstance(Key.get(FormatStoreManager.class, Names.named("test")));

    ContentRepositoryStore<?> contentRepositoryStore = underTest.contentRepositoryStore("content");
    ComponentStore<?> componentStore = underTest.componentStore("content");
    AssetStore<?> assetStore = underTest.assetStore("content");
    AssetBlobStore<?> assetBlobStore = underTest.assetBlobStore("content");

    // check the appropriate DAOs have been bound
    assertDaoBinding(contentRepositoryStore, TestContentRepositoryDAO.class);
    assertDaoBinding(componentStore, TestComponentDAO.class);
    assertDaoBinding(assetStore, TestAssetDAO.class);
    assertDaoBinding(assetBlobStore, TestAssetBlobDAO.class);

    // check that the asset store is the bespoke version
    assertThat(contentRepositoryStore, isA(ContentRepositoryStore.class));
    assertThat(componentStore, isA(ComponentStore.class));
    assertThat(assetStore, is(instanceOf(TestAssetStore.class)));
    assertThat(assetBlobStore, isA(AssetBlobStore.class));
  }

  @Test
  public void testBespokeOperations() {
    Injector injector =
        Guice.createInjector(new WireModule(new TestBespokeStoreModule(), new SessionModule(), new TransactionModule()));

    FormatStoreManager underTest = injector.getInstance(Key.get(FormatStoreManager.class, Names.named("test")));

    // our bespoke schema will be applied automatically via 'extendSchema'...

    ContentRepositoryStore<?> contentRepositoryStore = underTest.contentRepositoryStore("content");
    TestAssetStore assetStore = underTest.assetStore("content");

    ContentRepositoryData repository = new ContentRepositoryData();
    repository.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    repository.setConfigRepositoryId(new EntityUUID(combUUID()));
    contentRepositoryStore.createContentRepository(repository);

    TestAssetData asset = new TestAssetData();
    asset.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    asset.setRepositoryId(repository.repositoryId);
    asset.setPath("/path/to/asset");
    asset.setKind("test");
    assetStore.createAsset(asset);

    assertThat(assetStore.browseFlaggedAssets(repository.repositoryId, 10, null), is(emptyIterable()));

    asset.setTestFlag(true);
    assetStore.updateAssetFlag(asset);

    Optional<Asset> result = assetStore.browseFlaggedAssets(repository.repositoryId, 10, null).stream().findFirst();
    assertThat(result.get().path(), is("/path/to/asset"));
  }

  @Test
  public void testEventing() {
    Injector injector =
        Guice.createInjector(new WireModule(new TestPlainStoreModule(), new SessionModule(), new TransactionModule()));

    FormatStoreManager underTest = injector.getInstance(Key.get(FormatStoreManager.class, Names.named("test")));

    ContentRepositoryStore<?> contentRepositoryStore = underTest.contentRepositoryStore("content");
    ComponentStore<?> componentStore = underTest.componentStore("content");
    AssetStore<?> assetStore = underTest.assetStore("content");
    AssetBlobStore<?> assetBlobStore = underTest.assetBlobStore("content");

    ContentRepositoryData repository = new ContentRepositoryData();
    repository.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    repository.setConfigRepositoryId(new EntityUUID(combUUID()));
    contentRepositoryStore.createContentRepository(repository);

    ComponentData component = new ComponentData();
    component.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    component.setRepositoryId(repository.repositoryId);
    component.setNamespace("");
    component.setName("testComponent");
    component.setKind("aKind");
    component.setVersion("1.0");
    componentStore.createComponent(component);

    AssetData asset = new AssetData();
    asset.setAttributes(new NestedAttributesMap("attributes", new HashMap<>()));
    asset.setRepositoryId(repository.repositoryId);
    asset.setComponent(component);
    asset.setPath("/path/to/asset");
    asset.setKind("test");
    assetStore.createAsset(asset);

    AssetBlobData assetBlob = new AssetBlobData();
    assetBlob.setBlobRef(new BlobRef("local", "default", "testBlob"));
    assetBlob.setBlobSize(0);
    assetBlob.setContentType("text/plain");
    assetBlob.setChecksums(ImmutableMap.of());
    assetBlob.setBlobCreated(UTC.now());
    assetBlobStore.createAssetBlob(assetBlob);

    asset.setKind("jar");
    asset.setAssetBlob(assetBlob);

    assetStore.updateAssetAttributes(asset, new AttributeChangeSet(SET, "test-key", "test-asset"));
    assetStore.updateAssetKind(asset);
    assetStore.updateAssetBlobLink(asset);
    assetStore.markAsDownloaded(asset);

    component.setKind("pom");

    componentStore.updateComponentAttributes(component, SET, "test-key", "test-component");
    componentStore.updateComponentKind(component);

    assetStore.deleteAsset(asset);
    componentStore.deleteComponent(component);
    contentRepositoryStore.deleteContentRepository(repository);

    ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);

    verify(eventManager, times(15)).post(eventCaptor.capture());

    List<Object> events = eventCaptor.getAllValues();

    assertThat(events.get(0), instanceOf(ContentRepositoryCreatedEvent.class));
    assertThat(events.get(1), instanceOf(ComponentCreatedEvent.class));
    assertThat(events.get(2), instanceOf(AssetCreatedEvent.class));
    assertThat(events.get(3), instanceOf(AssetAttributesEvent.class));
    assertThat(events.get(4), instanceOf(AssetKindEvent.class));
    assertThat(events.get(5), instanceOf(AssetUpdatedEvent.class));
    assertThat(events.get(6), instanceOf(AssetDownloadedEvent.class));
    assertThat(events.get(7), instanceOf(ComponentAttributesEvent.class));
    assertThat(events.get(8), instanceOf(ComponentKindEvent.class));
    assertThat(events.get(9), instanceOf(AssetPreDeleteEvent.class));
    assertThat(events.get(10), instanceOf(AssetDeletedEvent.class));
    assertThat(events.get(11), instanceOf(ComponentPreDeleteEvent.class));
    assertThat(events.get(12), instanceOf(ComponentDeletedEvent.class));
    assertThat(events.get(13), instanceOf(ContentRepositoryPreDeleteEvent.class));
    assertThat(events.get(14), instanceOf(ContentRepositoryDeletedEvent.class));

    verifyNoMoreInteractions(eventManager);
  }

  // checks the DAO access provided by the store matches our expectations
  private static void assertDaoBinding(final ContentStoreSupport<?> store,
                                       final Class<? extends ContentDataAccess> daoClass)
  {
    // internal dao() method expects to be called from a transactional method, so mimic one here
    UnitOfWork.begin(store::openSession);
    try {
      Transactional.operation.run(() -> assertThat(store.dao(), is(instanceOf(daoClass))));
    }
    finally {
      UnitOfWork.end();
    }
  }
}
