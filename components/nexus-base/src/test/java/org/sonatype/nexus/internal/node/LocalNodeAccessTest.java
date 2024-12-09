package org.sonatype.nexus.internal.node;

import java.io.File;
import java.security.cert.Certificate;
import java.util.Collections;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.crypto.internal.CryptoHelperImpl;
import org.sonatype.nexus.ssl.KeyStoreManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Tests for local {@link NodeAccess}.
 */
@SuppressWarnings("HardCodedStringLiteral")
public class LocalNodeAccessTest
    extends TestSupport
{
  private KeyStoreManager keyStoreManager;

  private NodeAccess nodeAccess;

  @Before
  public void setUp() throws Exception {
    File dir = util.createTempDir("keystores");
    KeyStoreManagerConfigurationImpl config = new KeyStoreManagerConfigurationImpl();
    // use lower strength for faster test execution
    config.setKeyAlgorithmSize(512);
    keyStoreManager = new KeyStoreManagerImpl(new CryptoHelperImpl(), new KeyStoreStorageManagerImpl(dir), config);
    keyStoreManager.generateAndStoreKeyPair("a", "b", "c", "d", "e", "f");

    nodeAccess = new LocalNodeAccess(() -> keyStoreManager);
    nodeAccess.start();
  }

  @After
  public void tearDown() throws Exception {
    if (nodeAccess != null) {
      nodeAccess.stop();
    }
  }

  @Test
  public void idEqualToIdentityCertificate() throws Exception {
    Certificate cert = keyStoreManager.getCertificate();
    assertThat(nodeAccess.getId(), equalTo(NodeIdEncoding.nodeIdForCertificate(cert)));
  }

  @Test
  public void localIsOldestNode() {
    assertThat(nodeAccess.isOldestNode(), is(true));
  }

  @Test
  public void getMemberAliasesKeyValueEqualToIdentity() {
    assertThat(nodeAccess.getMemberAliases(),
        equalTo(Collections.singletonMap(nodeAccess.getId(), nodeAccess.getId())));
  }
}
