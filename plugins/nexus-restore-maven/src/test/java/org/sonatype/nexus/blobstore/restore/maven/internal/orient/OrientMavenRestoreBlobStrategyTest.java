package org.sonatype.nexus.blobstore.restore.maven.internal.orient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobId;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.restore.maven.internal.orient.OrientMavenRestoreBlobStrategy;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.common.log.DryRunPrefix;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.orient.maven.OrientMavenFacet;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPath.Coordinates;
import org.sonatype.nexus.repository.maven.internal.Maven2MavenPathParser;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.view.Content;

import com.google.common.collect.Maps;
import com.google.common.hash.HashCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class OrientMavenRestoreBlobStrategyTest
    extends TestSupport
{
  private static final String TEST_BLOB_STORE_NAME = "test";

  OrientMavenRestoreBlobStrategy underTest;

  @Mock
  Maven2MavenPathParser maven2MavenPathParser;

  @Mock
  NodeAccess nodeAccess;

  @Mock
  RepositoryManager repositoryManager;

  @Mock
  BlobStoreManager blobStoreManager;

  @Mock
  MavenPath mavenPath;

  @Mock
  Coordinates coordinates;

  @Mock
  Blob blob;

  @Mock
  BlobStore blobStore;

  @Mock
  BlobStoreConfiguration blobStoreConfiguration;

  @Mock
  Repository repository;

  @Mock
  StorageFacet storageFacet;

  @Mock
  StorageTx storageTx;

  @Mock
  Bucket bucket;

  @Mock
  OrientMavenFacet mavenFacet;

  @Mock
  DryRunPrefix dryRunPrefix;

  Properties properties = new Properties();

  byte[] blobBytes = "blobbytes".getBytes();

  @Before
  public void setup() throws IOException {
    underTest = new OrientMavenRestoreBlobStrategy(maven2MavenPathParser, nodeAccess, repositoryManager, blobStoreManager,
        dryRunPrefix);
    properties.setProperty("@BlobStore.blob-name", "org/codehaus/plexus/plexus/3.1/plexus-3.1.pom");
    properties.setProperty("@Bucket.repo-name", "test-repo");
    properties.setProperty("size", "1000");
    properties.setProperty("@BlobStore.content-type", "application/xml");
    properties.setProperty("sha1", "b64de86ceaa4f0e4d8ccc44a26c562c6fb7fb230");

    when(repositoryManager.get("test-repo")).thenReturn(repository);
    when(repository.optionalFacet(StorageFacet.class)).thenReturn(Optional.of(storageFacet));
    when(repository.optionalFacet(OrientMavenFacet.class)).thenReturn(Optional.of(mavenFacet));
    when(storageFacet.txSupplier()).thenReturn(() -> storageTx);

    when(storageTx.findBucket(repository)).thenReturn(bucket);

    when(blob.getId()).thenReturn(new BlobId("test"));
    when(blob.getInputStream()).thenReturn(new ByteArrayInputStream(blobBytes));

    when(maven2MavenPathParser.parsePath("org/codehaus/plexus/plexus/3.1/plexus-3.1.pom"))
        .thenReturn(mavenPath);

    when(mavenPath.getCoordinates()).thenReturn(coordinates);

    when(nodeAccess.getId()).thenReturn("node");


    when(blobStoreConfiguration.getName()).thenReturn(TEST_BLOB_STORE_NAME);

    when(blobStore.getBlobStoreConfiguration()).thenReturn(blobStoreConfiguration);

    when(repository.facet(OrientMavenFacet.class)).thenReturn(mavenFacet);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testRestore() throws Exception {
    underTest.restore(properties, blob, blobStore);
    verify(mavenFacet).get(mavenPath);
    verify(mavenFacet).put(eq(mavenPath), any(), eq(null));
    verifyNoMoreInteractions(mavenFacet);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testRestoreSkipNotFacet() {
    when(repository.optionalFacet(StorageFacet.class)).thenReturn(Optional.empty());
    underTest.restore(properties, blob, blobStore);
    verifyNoMoreInteractions(mavenFacet);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testRestoreSkipExistingContent() throws Exception {
    when(mavenFacet.get(mavenPath)).thenReturn(mock(Content.class));
    underTest.restore(properties, blob, blobStore);
    verify(mavenFacet).get(mavenPath);
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void testRestoreDryRun() throws Exception {
    underTest.restore(properties, blob, blobStore, true);
    verify(mavenFacet).get(mavenPath);
    verifyNoMoreInteractions(mavenFacet);
  }

  @Test
  public void testCorrectChecksums() throws Exception {
    Map<HashAlgorithm, HashCode> expectedHashes = Maps.newHashMap();
    expectedHashes.put(HashAlgorithm.MD5, HashAlgorithm.MD5.function().hashBytes(blobBytes));
    expectedHashes.put(HashAlgorithm.SHA1, HashAlgorithm.SHA1.function().hashBytes(blobBytes));
    expectedHashes.put(HashAlgorithm.SHA256, HashAlgorithm.SHA256.function().hashBytes(blobBytes));
    expectedHashes.put(HashAlgorithm.SHA512, HashAlgorithm.SHA512.function().hashBytes(blobBytes));
    ArgumentCaptor<AssetBlob> assetBlobCaptor = ArgumentCaptor.forClass(AssetBlob.class);

    underTest.restore(properties, blob, blobStore, false);
    verify(mavenFacet).get(mavenPath);
    verify(mavenFacet).put(eq(mavenPath), assetBlobCaptor.capture(), eq(null));

    assertEquals("asset hashes do not match blob", expectedHashes, assetBlobCaptor.getValue().getHashes());
  }
}
