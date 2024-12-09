package org.sonatype.nexus.blobstore.restore.maven.internal;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Properties;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.common.log.DryRunPrefix;
import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssetBuilder;
import org.sonatype.nexus.repository.content.fluent.FluentAssets;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPath.Coordinates;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.view.Payload;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Optional.empty;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.blobstore.api.BlobAttributesConstants.HEADER_PREFIX;
import static org.sonatype.nexus.blobstore.api.BlobStore.BLOB_NAME_HEADER;
import static org.sonatype.nexus.blobstore.api.BlobStore.CONTENT_TYPE_HEADER;
import static org.sonatype.nexus.blobstore.api.BlobStore.REPO_NAME_HEADER;

public class MavenRestoreBlobStrategyTest
    extends TestSupport
{
  private static final String REPO_NAME = "test-repo";

  private static final String TEST_BLOB_STORE_NAME = "test";

  private static final String BLOB_NAME = "org/codehaus/plexus/plexus/3.1/plexus-3.1.pom";

  @Mock
  private MavenPathParser mavenPathParser;

  @Mock
  private RepositoryManager repositoryManager;

  @Mock
  private MavenPath mavenPath;

  @Mock
  private Coordinates coordinates;

  @Mock
  private Blob blob;

  @Mock
  private BlobStore blobStore;

  @Mock
  private BlobStoreConfiguration blobStoreConfiguration;

  @Mock
  private Repository repository;

  @Mock
  private ContentFacet contentFacet;

  @Mock
  private MavenContentFacet mavenFacet;

  @Mock
  private DryRunPrefix dryRunPrefix;

  @Mock
  private FluentAssets assets;

  @Mock
  private FluentAssetBuilder fluentAssetBuilder;

  @Mock
  private FluentAsset asset;

  @Mock
  private FluentComponent component;

  Properties properties;

  byte[] blobBytes = "blobbytes".getBytes();

  MavenRestoreBlobStrategy underTest;

  @Before
  public void setup() {
    properties = new Properties();
    properties.setProperty(HEADER_PREFIX + REPO_NAME_HEADER, REPO_NAME);
    properties.setProperty(HEADER_PREFIX + BLOB_NAME_HEADER, BLOB_NAME);
    properties.setProperty(HEADER_PREFIX + CONTENT_TYPE_HEADER, "application/xml");
    properties.setProperty("size", "1000");
    properties.setProperty("sha1", "b64de86ceaa4f0e4d8ccc44a26c562c6fb7fb230");

    when(repositoryManager.get(REPO_NAME)).thenReturn(repository);

    when(contentFacet.assets()).thenReturn(assets);
    when(assets.path(anyString())).thenReturn(fluentAssetBuilder);
    when(fluentAssetBuilder.find()).thenReturn(Optional.of(asset));

    when(asset.component()).thenReturn(empty());

    when(repository.facet(ContentFacet.class)).thenReturn(contentFacet);
    when(repository.optionalFacet(MavenContentFacet.class)).thenReturn(Optional.of(mavenFacet));
    when(repository.facet(MavenContentFacet.class)).thenReturn(mavenFacet);

    when(blob.getInputStream()).thenReturn(new ByteArrayInputStream(blobBytes));

    when(mavenPathParser.parsePath(BLOB_NAME)).thenReturn(mavenPath);
    when(mavenPathParser.isRepositoryMetadata(mavenPath)).thenReturn(false);
    when(mavenPathParser.isRepositoryIndex(mavenPath)).thenReturn(false);
    when(mavenPath.getCoordinates()).thenReturn(coordinates);

    when(blobStoreConfiguration.getName()).thenReturn(TEST_BLOB_STORE_NAME);

    when(blobStore.getBlobStoreConfiguration()).thenReturn(blobStoreConfiguration);

    underTest = new MavenRestoreBlobStrategy(dryRunPrefix, repositoryManager, mavenPathParser);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testRestore() throws Exception {
    underTest.restore(properties, blob, blobStore);
    verify(mavenFacet).put(eq(mavenPath), any(Payload.class));
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void testRestoreDryRun() throws Exception {
    underTest.restore(properties, blob, blobStore, true);
    verifyNoMoreInteractions(mavenFacet);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testRestoreSkipNotFacet() {
    when(repository.optionalFacet(MavenContentFacet.class)).thenReturn(Optional.empty());
    underTest.restore(properties, blob, blobStore);
    verifyNoMoreInteractions(mavenFacet);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testRestoreSkipExistingContent() throws Exception {
    when(asset.component()).thenReturn(Optional.of(component));
    underTest.restore(properties, blob, blobStore);
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void missingCoordinates() throws Exception {
    when(mavenPath.getCoordinates()).thenReturn(null);
    underTest.restore(properties, blob, blobStore, false);
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void pathIsIndex() throws Exception {
    when(mavenPathParser.isRepositoryIndex(mavenPath)).thenReturn(true);
    underTest.restore(properties, blob, blobStore, false);
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void pathIsMetadata() throws Exception {
    when(mavenPathParser.isRepositoryMetadata(mavenPath)).thenReturn(true);
    underTest.restore(properties, blob, blobStore, false);
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void testMissingContentFacet() throws Exception {
    when(repository.optionalFacet(MavenContentFacet.class)).thenReturn(Optional.empty());
    underTest.restore(properties, blob, blobStore, false);
    verifyNoMoreInteractions(mavenFacet);
  }
}
