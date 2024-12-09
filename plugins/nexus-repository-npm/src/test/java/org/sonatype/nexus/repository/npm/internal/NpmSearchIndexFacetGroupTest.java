package org.sonatype.nexus.repository.npm.internal;

import java.util.function.Supplier;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.npm.orient.internal.search.legacy.NpmSearchIndexFacetGroup;
import org.sonatype.nexus.repository.npm.orient.internal.search.legacy.NpmSearchIndexInvalidatedEvent;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.transaction.TransactionModule;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmFacetUtils.REPOSITORY_ROOT_ASSET;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;

public class NpmSearchIndexFacetGroupTest
    extends TestSupport
{
  @Mock
  private StorageTx storageTx;

  @Mock
  private Bucket groupBucket;

  @Mock
  private EventManager eventManager;

  @Mock
  private Repository eventRepository;

  @Mock
  private Repository groupRepository;

  @Mock
  private GroupFacet groupFacet;

  @Mock
  private StorageFacet storageFacet;

  @Mock
  private Asset asset;

  private Supplier<StorageTx> supplierStorageTx = () -> storageTx;

  private NpmSearchIndexFacetGroup underTest;

  @Before
  public void setUp() throws Exception {
    underTest = Guice.createInjector(new TransactionModule(), new AbstractModule()
    {
      @Override
      protected void configure() {
        bind(EventManager.class).toInstance(eventManager);
      }
    }).getInstance(NpmSearchIndexFacetGroup.class);

    underTest.attach(groupRepository);

    when(groupRepository.facet(GroupFacet.class)).thenReturn(groupFacet);
    when(groupRepository.facet(StorageFacet.class)).thenReturn(storageFacet);
    when(storageFacet.txSupplier()).thenReturn(supplierStorageTx);
    when(storageTx.findAssetWithProperty(P_NAME, REPOSITORY_ROOT_ASSET, groupBucket)).thenReturn(asset);
    when(storageTx.findBucket(groupRepository)).thenReturn(groupBucket);
  }

  @Test
  public void whenRepositoryIsNotAMemberOfGroup_shouldNotInvalidateCache() {
    when(groupFacet.member(eventRepository)).thenReturn(true);

    underTest.on(new NpmSearchIndexInvalidatedEvent(eventRepository));

    isInvalidateCacheCalled(1);
  }

  @Test
  public void whenRepositoryIsAMemberOfGroup_shouldInvalidateCache() {
    when(groupFacet.member(eventRepository)).thenReturn(false);

    underTest.on(new NpmSearchIndexInvalidatedEvent(eventRepository));

    isInvalidateCacheCalled(0);
  }

  private void isInvalidateCacheCalled(final int i) {
    verify(storageTx, times(i)).deleteAsset(asset);
    verify(eventManager, times(i)).post(any(NpmSearchIndexInvalidatedEvent.class));
  }
}
