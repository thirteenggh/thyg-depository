package org.sonatype.nexus.testsuite.raw;

import java.io.File;

import javax.inject.Inject;

import org.sonatype.goodies.httpfixture.server.fluent.Behaviours;
import org.sonatype.goodies.httpfixture.server.fluent.Server;
import org.sonatype.goodies.httpfixture.server.jetty.behaviour.Content;
import org.sonatype.nexus.content.testsuite.groups.OrientAndSQLTestGroup;
import org.sonatype.nexus.content.testsuite.groups.OrientTestGroup;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.testsuite.helpers.ComponentAssetTestHelper;
import org.sonatype.nexus.testsuite.testsupport.blobstore.restore.BlobstoreRestoreTestHelper;
import org.sonatype.nexus.testsuite.testsupport.raw.RawClient;
import org.sonatype.nexus.testsuite.testsupport.raw.RawITSupport;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.apache.commons.lang3.StringUtils.prependIfMissing;
import static org.apache.http.entity.ContentType.TEXT_PLAIN;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.sonatype.nexus.repository.http.HttpStatus.OK;

public class RawRestoreBlobIT
    extends RawITSupport
{
  private static final String HOSTED_REPO_NAME = "raw-hosted";

  private static final String PROXY_REPO_NAME = "raw-proxy";

  private static final String TEST_CONTENT = "alphabet.txt";

  private Server proxyServer;

  private Repository hostedRepository;

  private Repository proxyRepository;

  private RawClient hostedClient;

  private RawClient proxyClient;

  @Inject
  private BlobstoreRestoreTestHelper restoreTestHelper;

  @Inject
  private ComponentAssetTestHelper componentAssetTestHelper;

  @Before
  public void setup() throws Exception {
    hostedRepository = repos.createRawHosted(HOSTED_REPO_NAME);
    hostedClient = rawClient(hostedRepository);

    proxyServer = Server.withPort(0).start();
    proxyServer.serve("/" + TEST_CONTENT).withBehaviours(resolveFile(TEST_CONTENT));

    proxyRepository = repos.createRawProxy(PROXY_REPO_NAME, "http://localhost:" + proxyServer.getPort() + "/");
    proxyClient = rawClient(proxyRepository);

    File testFile = resolveTestFile(TEST_CONTENT);
    assertThat(hostedClient.put(TEST_CONTENT, TEXT_PLAIN, testFile), is(HttpStatus.CREATED));
    assertThat(proxyClient.get(TEST_CONTENT).getStatusLine().getStatusCode(), is(OK));
  }

  @Category(OrientAndSQLTestGroup.class)
  @Test
  public void testMetadataRestoreWhenBothAssetsAndComponentsAreMissing() throws Exception {
    verifyMetadataRestored(restoreTestHelper::simulateComponentAndAssetMetadataLoss);
  }

  @Category(OrientAndSQLTestGroup.class)
  @Test
  public void testMetadataRestoreWhenOnlyAssetsAreMissing() throws Exception {
    verifyMetadataRestored(restoreTestHelper::simulateAssetMetadataLoss);
  }

  @Category(OrientTestGroup.class)
  @Test
  public void testMetadataRestoreWhenOnlyComponentsAreMissing() throws Exception {
    verifyMetadataRestored(restoreTestHelper::simulateComponentMetadataLoss);
  }

  @Category(OrientAndSQLTestGroup.class)
  @Test
  public void testDryRunRestore()
  {
    assertTrue(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
    restoreTestHelper.simulateComponentAndAssetMetadataLoss();
    assertFalse(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
    restoreTestHelper.runRestoreMetadataTaskWithTimeout(10, true);
    assertFalse(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
  }

  @Category(OrientAndSQLTestGroup.class)
  @Test
  public void testNotDryRunRestore()
  {
    assertTrue(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
    restoreTestHelper.simulateComponentAndAssetMetadataLoss();
    assertFalse(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
    restoreTestHelper.runRestoreMetadataTaskWithTimeout(10, false);
    assertTrue(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
  }

  private void verifyMetadataRestored(final Runnable metadataLossSimulation) throws Exception {
    metadataLossSimulation.run();

    restoreTestHelper.runRestoreMetadataTask();

    assertTrue(componentAssetTestHelper.assetExists(proxyRepository, TEST_CONTENT));
    assertTrue(componentAssetTestHelper.assetExists(hostedRepository, TEST_CONTENT));

    assertTrue(componentExists(hostedRepository, TEST_CONTENT));
    assertTrue(componentExists(proxyRepository, TEST_CONTENT));

    restoreTestHelper.assertAssetMatchesBlob(hostedRepository, TEST_CONTENT);
    restoreTestHelper.assertAssetMatchesBlob(proxyRepository, TEST_CONTENT);

    assertTrue(assetWithComponentExists(hostedRepository, TEST_CONTENT, "/", TEST_CONTENT));
    assertTrue(assetWithComponentExists(proxyRepository, TEST_CONTENT, "/", TEST_CONTENT));

    assertThat(hostedClient.get(TEST_CONTENT).getStatusLine().getStatusCode(), is(OK));
    assertThat(proxyClient.get(TEST_CONTENT).getStatusLine().getStatusCode(), is(OK));
  }

  private boolean componentExists(final Repository repository, final String name) {
    return componentAssetTestHelper.componentExists(repository, name)
        || componentAssetTestHelper.componentExists(repository, prependIfMissing(name, "/"));
  }

  private boolean assetWithComponentExists(final Repository repository, final String path, final String group, final String name) {
    return componentAssetTestHelper.assetWithComponentExists(repository, path, group, name)
        || componentAssetTestHelper.assetWithComponentExists(hostedRepository, prependIfMissing(path, "/"), group, prependIfMissing(name, "/"));
  }

  private Content resolveFile(final String filename) {
    return Behaviours.file(testData.resolveFile(filename));
  }
}
