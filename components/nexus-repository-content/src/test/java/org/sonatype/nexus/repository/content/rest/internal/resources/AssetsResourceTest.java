package org.sonatype.nexus.repository.content.rest.internal.resources;

import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.common.entity.DetachedEntityId;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssets;
import org.sonatype.nexus.repository.content.fluent.internal.FluentAssetImpl;
import org.sonatype.nexus.repository.content.maintenance.MaintenanceService;
import org.sonatype.nexus.repository.content.store.AssetData;
import org.sonatype.nexus.repository.rest.api.AssetXO;
import org.sonatype.nexus.repository.rest.api.RepositoryItemIDXO;
import org.sonatype.nexus.repository.rest.api.RepositoryManagerRESTAdapter;
import org.sonatype.nexus.repository.selector.ContentAuthHelper;
import org.sonatype.nexus.rest.Page;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.Base64.getUrlEncoder;
import static java.util.Collections.emptyMap;
import static java.util.Optional.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.content.rest.internal.resources.AssetsResourceSupport.LIMIT;
import static org.sonatype.nexus.repository.content.store.InternalIds.toExternalId;

public class AssetsResourceTest
    extends TestSupport
{
  private static final String ASSET_PATH = "/junit/junit/4.12/junit-4.12.jar";

  private static final String REPOSITORY_NAME = "repository1";

  private static final String REPOSITORY_URL = "http://localhost:8081/repository/" + REPOSITORY_NAME;

  private static final int AN_ASSET_ID = 1;

  private static final String A_FORMAT = "aFormatValue";

  @Mock
  private Format aFormat;

  @Mock
  private FluentAssets fluentAssets;

  @Mock
  private Repository repository;

  @Mock
  private ContentFacet contentFacet;

  @Mock
  private ContentFacetSupport contentFacetSupport;

  @Mock
  private RepositoryManagerRESTAdapter repositoryManagerRESTAdapter;

  @Mock
  private MaintenanceService maintenanceService;

  @Mock
  private Continuation<FluentAsset> assetContinuation;

  @Mock
  private ContentAuthHelper contentAuthHelper;


  @InjectMocks
  private AssetsResource underTest;

  @Before
  public void setup() {
    mockRepository();
    when(contentAuthHelper.checkPathPermissions(ASSET_PATH, A_FORMAT, repository.getName())).thenReturn(true);
  }

  @Test
  public void getAssetsShouldReturnAPageAssets() {
    Page<AssetXO> assets = underTest.getAssets(null, REPOSITORY_NAME);

    assertNotNull(assets);

    verify(fluentAssets).browse(LIMIT, null);
  }

  @Test
  public void getAssetByIdShouldReturnAnAssetWhenFound() {
    when(fluentAssets.find(new DetachedEntityId(AN_ASSET_ID + "")))
        .thenReturn(Optional.of(aFluentAsset()));

    AssetXO assetXO = underTest.getAssetById(anEncodedAssetId());

    assertThat(assetXO, is(anAssetXO()));
  }

  @Test(expected = NotFoundException.class)
  public void getAssetByIdShouldThrowNotFoundExceptionWhenNotFound() {
    when(fluentAssets.find(new DetachedEntityId(AN_ASSET_ID + ""))).thenReturn(empty());

    AssetXO assetXO = underTest.getAssetById(anEncodedAssetId());

    assertThat(assetXO, is(anAssetXO()));
  }

  @Test
  public void deleteAssetShouldDeleteAsset() {
    FluentAssetImpl assetToDelete = aFluentAsset();
    when(fluentAssets.find(any())).thenReturn(Optional.of(assetToDelete));

    underTest.deleteAsset(anEncodedAssetId());

    verify(maintenanceService).deleteAsset(repository, assetToDelete);
  }

  @Test(expected = NotFoundException.class)
  public void deleteAssetShouldThrowNotFoundExceptionWhenNotFound() {
    when(fluentAssets.find(new DetachedEntityId(AN_ASSET_ID + ""))).thenReturn(Optional.empty());

    underTest.deleteAsset(anEncodedAssetId());
  }

  private void mockRepository() {
    when(repositoryManagerRESTAdapter.getRepository(REPOSITORY_NAME)).thenReturn(repository);
    when(repository.facet(ContentFacet.class)).thenReturn(contentFacet);
    when(contentFacet.assets()).thenReturn(fluentAssets);
    when(fluentAssets.browse(LIMIT, null)).thenReturn(assetContinuation);
    when(assetContinuation.isEmpty()).thenReturn(true);
    when(repository.getName()).thenReturn(REPOSITORY_NAME);
    when(repository.getUrl()).thenReturn(REPOSITORY_URL);
    when(repository.getFormat()).thenReturn(aFormat);
    when(aFormat.getValue()).thenReturn(A_FORMAT);
  }

  private FluentAssetImpl aFluentAsset() {
    return new FluentAssetImpl(contentFacetSupport, anAsset());
  }

  private AssetData anAsset() {
    AssetData asset = new AssetData();
    asset.setAssetId(AN_ASSET_ID);
    asset.setPath(ASSET_PATH);
    return asset;
  }

  private AssetXO anAssetXO() {
    return AssetXO.builder()
        .path(ASSET_PATH)
        .downloadUrl(REPOSITORY_URL + ASSET_PATH)
        .id(new RepositoryItemIDXO(REPOSITORY_NAME, toExternalId(AN_ASSET_ID).getValue()).getValue())
        .repository(REPOSITORY_NAME)
        .checksum(emptyMap())
        .build();
  }

  private String anEncodedAssetId() {
    return getUrlEncoder().encodeToString((REPOSITORY_NAME + ":" + AN_ASSET_ID).getBytes());
  }
}
