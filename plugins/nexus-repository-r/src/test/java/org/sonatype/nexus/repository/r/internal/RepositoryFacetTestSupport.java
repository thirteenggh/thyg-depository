package org.sonatype.nexus.repository.r.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.transaction.UnitOfWork;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public abstract class RepositoryFacetTestSupport<T extends Facet>
    extends TestSupport
{
  protected List<Asset> assets;

  @Mock
  protected StorageTx storageTx;

  @Mock
  protected StorageFacet storageFacet;

  @Mock
  protected Bucket bucket;

  @Mock
  protected Repository repository;

  @Mock
  protected Asset asset;

  @Mock
  protected NestedAttributesMap formatAttributes;

  @Mock
  protected NestedAttributesMap attributes;

  @Mock
  protected Blob blob;

  protected T underTest;

  @Before
  public void initialiseFacetMocksAndSetupTransaction() throws Exception {
    assets = new ArrayList<>();
    UnitOfWork.beginBatch(storageTx);
    when(storageTx.browseAssets(any(Bucket.class))).thenReturn(assets);
    when(storageTx.browseAssets(any(), any(Bucket.class))).thenReturn(assets);
    when(storageTx.findAssetWithProperty(anyString(), anyString(), any(Bucket.class))).thenReturn(asset);
    when(storageTx.findBucket(repository)).thenReturn(bucket);
    when(storageTx.requireBlob(any())).thenReturn(blob);
    when(repository.facet(StorageFacet.class)).thenReturn(storageFacet);
    when(asset.formatAttributes()).thenReturn(formatAttributes);
    when(asset.attributes()).thenReturn(attributes);
    when(attributes.get("last_modified", Date.class)).thenReturn(new Date());
    when(attributes.get("last_verified", Date.class)).thenReturn(new Date());
    when(attributes.get("cache_token", String.class)).thenReturn("test");
    when(attributes.child("content")).thenReturn(attributes);
    when(attributes.child("cache")).thenReturn(attributes);
    underTest = initialiseSystemUnderTest();
    underTest.attach(repository);
  }

  @After
  public void tearDown() {
    UnitOfWork.end();
  }

  protected abstract T initialiseSystemUnderTest();
}
