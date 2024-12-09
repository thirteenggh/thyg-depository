package org.sonatype.nexus.repository.maintenance.internal;

import java.util.Collections;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.IllegalOperationException;
import org.sonatype.nexus.repository.MissingFacetException;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.RepositoryPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapterManager;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetVariableResolver;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentMaintenance;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.security.BreadActions;
import org.sonatype.nexus.security.subject.FakeAlmightySubject;
import org.sonatype.nexus.selector.VariableSource;

import java.util.function.Supplier;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.util.ThreadContext;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MaintenanceServiceImplTest
    extends TestSupport
{
  @Mock
  ContentPermissionChecker contentPermissionChecker;

  @Mock
  VariableResolverAdapterManager variableResolverAdapterManager;

  @Mock
  Format format;

  @Mock
  Repository mavenReleases;

  @Mock
  Repository mavenGroup;

  @Mock
  AssetVariableResolver assetVariableResolver;

  @Mock
  Asset assetOne;

  @Mock
  VariableSource variableSource;

  @Mock
  EntityMetadata assetEntityMetadata;

  @Mock
  EntityId assetEntityId;

  @Mock
  ComponentMaintenance componentMaintenance;

  @Mock
  Component component;

  @Mock
  EntityMetadata componentEntityMetadata;

  @Mock
  EntityId componentEntityId;

  @Mock
  StorageFacet storageFacet;

  @Mock
  Supplier<StorageTx> txSupplier;

  @Mock
  StorageTx storageTx;

  @Mock
  DeleteFolderService deleteFolderService;

  @Mock
  RepositoryPermissionChecker repositoryPermissionChecker;

  MaintenanceServiceImpl underTest;

  @Before
  public void setUp() throws Exception {
    when(format.toString()).thenReturn("maven2");
    when(format.getValue()).thenReturn("maven2");

    setupRepository();
    setupStorage();
    setupRepositoryGroup();
    setupVariableResolvers();
    setupAssetComponents();

    underTest = new MaintenanceServiceImpl(contentPermissionChecker, variableResolverAdapterManager,
        deleteFolderService, repositoryPermissionChecker);
  }

  private void setupAssetComponents() {
    when(assetOne.getEntityMetadata()).thenReturn(assetEntityMetadata);
    when(assetEntityMetadata.getId()).thenReturn(assetEntityId);

    when(component.getEntityMetadata()).thenReturn(componentEntityMetadata);
    when(componentEntityMetadata.getId()).thenReturn(componentEntityId);
  }

  private void setupVariableResolvers() {
    when(variableResolverAdapterManager.get("maven2")).thenReturn(assetVariableResolver);
    when(assetVariableResolver.fromAsset(assetOne)).thenReturn(variableSource);
  }

  private void setupRepositoryGroup() {
    when(mavenGroup.getName()).thenReturn("maven-group");
    when(mavenGroup.getFormat()).thenReturn(format);
    
    doThrow(new MissingFacetException(mavenGroup, ComponentMaintenance.class)).when(mavenGroup)
        .facet(ComponentMaintenance.class);
  }

  private void setupStorage() {
    when(storageFacet.txSupplier()).thenReturn(txSupplier);
    when(txSupplier.get()).thenReturn(storageTx);
  }

  private void setupRepository() {
    when(mavenReleases.getName()).thenReturn("maven-releases");
    when(mavenReleases.getFormat()).thenReturn(format);
    when(mavenReleases.facet(ComponentMaintenance.class)).thenReturn(componentMaintenance);
    when(mavenReleases.facet(StorageFacet.class)).thenReturn(storageFacet);
  }

  @Test
  public void testDeleteAsset() {
    when(contentPermissionChecker.isPermitted("maven-releases", "maven2", BreadActions.DELETE, variableSource))
        .thenReturn(true);
    when(componentMaintenance.deleteAsset(assetEntityId)).thenReturn(Collections.singleton("assetname"));

    assertThat(underTest.deleteAsset(mavenReleases, assetOne), contains("assetname"));
  }

  @Test(expected = IllegalOperationException.class)
  public void testDeleteAsset_notSupported() {
    when(contentPermissionChecker.isPermitted("maven-group", "maven2", BreadActions.DELETE, variableSource))
        .thenReturn(true);

    underTest.deleteAsset(mavenGroup, assetOne);
  }

  @Test(expected = AuthorizationException.class)
  public void testDeleteAsset_notPermitted() {
    when(contentPermissionChecker.isPermitted("maven-releases", "maven2", BreadActions.DELETE, variableSource))
        .thenReturn(false);

    underTest.deleteAsset(mavenReleases, assetOne);
  }

  @Test
  public void testDeleteComponent() {
    when(storageTx.browseAssets(component)).thenReturn(singletonList(assetOne));
    when(contentPermissionChecker.isPermitted("maven-releases", "maven2", BreadActions.DELETE, variableSource))
        .thenReturn(true);

    underTest.deleteComponent(mavenReleases, component);

    verify(storageTx).begin();
    verify(storageTx).close();
    verify(componentMaintenance).deleteComponent(componentEntityId);
  }

  @Test(expected = AuthorizationException.class)
  public void testDeleteComponent_NotAuthorized() {
    when(storageTx.browseAssets(component)).thenReturn(singletonList(assetOne));
    when(contentPermissionChecker.isPermitted("maven-releases", "maven2", BreadActions.DELETE, variableSource))
        .thenReturn(false);

    underTest.deleteComponent(mavenReleases, component);
  }

  @Test
  public void testDeleteFolder() {
    ThreadContext.bind(FakeAlmightySubject.forUserId("disabled-security"));
    when(repositoryPermissionChecker.userCanDeleteInRepository(mavenReleases)).thenReturn(true);

    underTest.deleteFolder(mavenReleases, "someFolder");

    verify(deleteFolderService, timeout(500)).deleteFolder(eq(mavenReleases), eq("someFolder"), any(DateTime.class), any());
  }

  @Test(expected = AuthorizationException.class)
  public void testDeleteFolder_NotAuthorized() {
    when(contentPermissionChecker.isPermitted("maven-releases", "maven2", BreadActions.DELETE, variableSource))
        .thenReturn(false);
    underTest.deleteFolder(mavenReleases, "somePath");
  }
}
