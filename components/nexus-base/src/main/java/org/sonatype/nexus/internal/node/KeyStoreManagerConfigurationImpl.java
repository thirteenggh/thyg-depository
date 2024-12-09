package org.sonatype.nexus.internal.node;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.Time;
import org.sonatype.nexus.ssl.KeyStoreManagerConfiguration;
import org.sonatype.nexus.ssl.KeyStoreManagerConfigurationSupport;

import com.google.common.annotations.VisibleForTesting;

/**
 * Node {@link KeyStoreManagerConfiguration}.
 *
 * @since 3.0
 */
@Named(KeyStoreManagerImpl.NAME)
@Singleton
public class KeyStoreManagerConfigurationImpl
    extends KeyStoreManagerConfigurationSupport
{
  private static final String CPREFIX = "${node.keyStoreManager";

  /**
   * Private key-store password.
   */
  private static final char[] PKSP = "uuPWrk3UEQRaolpd".toCharArray();

  /**
   * Trusted key-store password.
   */
  private static final char[] TKSP = "1bmcqcHV3sp6fVKD".toCharArray();

  /**
   * Private-key password.
   */
  private static final char[] PKP = "CyQM8zCFeorarTA8".toCharArray();

  @Inject
  public KeyStoreManagerConfigurationImpl(
      @Named(CPREFIX + ".keyStoreType:-JKS}") final String keyStoreType,
      @Named(CPREFIX + ".keyAlgorithm:-RSA}") final String keyAlgorithm,
      @Named(CPREFIX + ".keyAlgorithmSize:-2048}") final int keyAlgorithmSize,
      @Named(CPREFIX + ".certificateValidity:-36500d}") final Time certificateValidity,
      @Named(CPREFIX + ".signatureAlgorithm:-SHA1WITHRSA}") final String signatureAlgorithm,
      @Named(CPREFIX + ".keyManagerAlgorithm:-DEFAULT}") final String keyManagerAlgorithm,
      @Named(CPREFIX + ".trustManagerAlgorithm:-DEFAULT}") final String trustManagerAlgorithm)
  {
    setPrivateKeyStorePassword(PKSP);
    setTrustedKeyStorePassword(TKSP);
    setPrivateKeyPassword(PKP);
    setKeyStoreType(keyStoreType);
    setKeyAlgorithm(keyAlgorithm);
    setKeyAlgorithmSize(keyAlgorithmSize);
    setCertificateValidity(certificateValidity);
    setSignatureAlgorithm(signatureAlgorithm);
    setKeyManagerAlgorithm(keyManagerAlgorithm);
    setTrustManagerAlgorithm(trustManagerAlgorithm);
  }

  @VisibleForTesting
  public KeyStoreManagerConfigurationImpl() {
    setPrivateKeyStorePassword(PKSP);
    setTrustedKeyStorePassword(TKSP);
    setPrivateKeyPassword(PKP);
  }
}
