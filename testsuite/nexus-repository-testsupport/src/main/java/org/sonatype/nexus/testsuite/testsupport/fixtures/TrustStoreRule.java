package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Provider;

import org.sonatype.nexus.ssl.CertificateUtil;
import org.sonatype.nexus.ssl.KeystoreException;
import org.sonatype.nexus.ssl.TrustStore;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 3.19
 */
public class TrustStoreRule
    extends ExternalResource
{
  private static final Logger log = LoggerFactory.getLogger(TrustStoreRule.class);

  private final Provider<TrustStore> trustStoreProvider;

  private Set<String> managedAliases = new HashSet<>();

  public TrustStoreRule(final Provider<TrustStore> trustStoreProvider) {
    this.trustStoreProvider = trustStoreProvider;
  }

  @Override
  protected void after() {
    managedAliases.forEach(fingerprint -> {
      try {
        trustStoreProvider.get().removeTrustCertificate(fingerprint);
      }
      catch (Exception e) { // NOSONAR
        log.info("Unable to clean up alias {}", fingerprint, e);
      }
    });
  }

  public void addCertificate(final String pem) {
    try {
      String fingerprint = CertificateUtil.calculateFingerprint(CertificateUtil.decodePEMFormattedCertificate(pem));
      trustStoreProvider.get().importTrustCertificate(pem, fingerprint);
      managedAliases.add(fingerprint);
    }
    catch (CertificateException | KeystoreException e) {
      throw new RuntimeException("Failed to add certificate", e);
    }
  }

  /**
   * Add a certificate alias to automatically cleanup upon test failure
   */
  public void manageAlias(final String fingerprint) {
    managedAliases.add(fingerprint);
  }

  /**
   * Add a certificate alias to automatically cleanup upon test failure
   */
  public void unmanageAlias(final String fingerprint) {
    managedAliases.remove(fingerprint);
  }
}
