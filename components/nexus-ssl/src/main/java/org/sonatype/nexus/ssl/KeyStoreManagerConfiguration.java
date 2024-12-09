package org.sonatype.nexus.ssl;

import org.sonatype.goodies.common.Time;

/**
 * {@link KeyStoreManager} configuration.
 *
 * @since 3.0
 */
public interface KeyStoreManagerConfiguration
{
  String getKeyStoreType();

  String getKeyAlgorithm();

  int getKeyAlgorithmSize();

  Time getCertificateValidity();

  String getSignatureAlgorithm();

  String getKeyManagerAlgorithm();

  String getTrustManagerAlgorithm();

  char[] getPrivateKeyStorePassword();

  char[] getTrustedKeyStorePassword();

  char[] getPrivateKeyPassword();
}
