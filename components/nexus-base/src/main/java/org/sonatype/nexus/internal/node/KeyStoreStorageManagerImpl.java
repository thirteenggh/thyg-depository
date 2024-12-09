package org.sonatype.nexus.internal.node;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.ssl.spi.KeyStoreStorage;
import org.sonatype.nexus.ssl.spi.KeyStoreStorageManager;

import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link KeyStoreStorageManager} for the node identity. Uses local filesystem as backing storage so
 * that node identity is specific to each node.
 * 
 * @since 3.1
 */
@Named(KeyStoreManagerImpl.NAME)
@Singleton
public class KeyStoreStorageManagerImpl
    implements KeyStoreStorageManager
{
  private final File basedir;

  @Inject
  public KeyStoreStorageManagerImpl(final ApplicationDirectories directories) {
    this.basedir = new File(directories.getWorkDirectory("keystores"), KeyStoreManagerImpl.NAME);
  }

  @VisibleForTesting
  public KeyStoreStorageManagerImpl(final File basedir) {
    this.basedir = checkNotNull(basedir);
  }

  @Override
  public KeyStoreStorage createStorage(final String keyStoreName) {
    checkNotNull(keyStoreName);
    return new FileKeyStoreStorage(new File(basedir, keyStoreName));
  }
}
