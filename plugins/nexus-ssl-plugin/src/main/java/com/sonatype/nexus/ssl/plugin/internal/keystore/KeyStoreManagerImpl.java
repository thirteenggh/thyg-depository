package com.sonatype.nexus.ssl.plugin.internal.keystore;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.crypto.CryptoHelper;
import org.sonatype.nexus.ssl.KeyStoreManagerConfiguration;
import org.sonatype.nexus.ssl.spi.KeyStoreStorageManager;

/**
 * SSL plugin specific key-store manager.
 *
 * @since ssl 1.0
 */
@Named(KeyStoreManagerImpl.NAME)
@Singleton
public class KeyStoreManagerImpl
    extends org.sonatype.nexus.ssl.KeyStoreManagerImpl
{
  public static final String NAME = "ssl";

  @Inject
  public KeyStoreManagerImpl(final CryptoHelper crypto,
                             @Named(NAME) final KeyStoreStorageManager storageManager,
                             @Named(NAME) final KeyStoreManagerConfiguration config)
  {
    super(crypto, storageManager, config);
  }
}
