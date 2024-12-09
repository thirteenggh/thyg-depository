package org.sonatype.nexus.internal.node;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.crypto.CryptoHelper;
import org.sonatype.nexus.ssl.KeyStoreManager;
import org.sonatype.nexus.ssl.KeyStoreManagerConfiguration;
import org.sonatype.nexus.ssl.spi.KeyStoreStorageManager;

/**
 * Node {@link KeyStoreManager}.
 *
 * @since 3.0
 */
@Named(KeyStoreManagerImpl.NAME)
@Singleton
public class KeyStoreManagerImpl
    extends org.sonatype.nexus.ssl.KeyStoreManagerImpl
{
  public static final String NAME = "node";

  @Inject
  public KeyStoreManagerImpl(final CryptoHelper crypto,
                             @Named(NAME) final KeyStoreStorageManager storageManager,
                             @Named(NAME) final KeyStoreManagerConfiguration config)
  {
    super(crypto, storageManager, config);
  }
}
