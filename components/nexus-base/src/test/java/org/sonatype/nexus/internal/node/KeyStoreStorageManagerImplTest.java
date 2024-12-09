package org.sonatype.nexus.internal.node;

import java.io.File;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.ssl.spi.KeyStoreStorage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class KeyStoreStorageManagerImplTest
    extends TestSupport
{
  private File keyStoreDir = util.createTempDir("keystores");

  @Mock
  private ApplicationDirectories appDirs;

  private KeyStoreStorageManagerImpl storageManager;

  @Before
  public void setUp() {
    when(appDirs.getWorkDirectory("keystores")).thenReturn(keyStoreDir);
    storageManager = new KeyStoreStorageManagerImpl(appDirs);
  }

  @Test
  public void testCreateStorage() {
    KeyStoreStorage storage = storageManager.createStorage("test.ks");
    assertThat(storage, is(instanceOf(FileKeyStoreStorage.class)));
    assertThat(((FileKeyStoreStorage) storage).getKeyStoreFile(), is(new File(keyStoreDir, "node/test.ks")));
  }
}
