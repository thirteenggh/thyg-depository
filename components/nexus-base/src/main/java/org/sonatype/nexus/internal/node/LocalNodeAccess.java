package org.sonatype.nexus.internal.node;

import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.ssl.CertificateUtil;
import org.sonatype.nexus.ssl.KeyStoreManager;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport.State.STARTED;

/**
 * Local {@link NodeAccess}.
 *
 * @since 3.0
 */
@Named("local")
@Singleton
public class LocalNodeAccess
    extends StateGuardLifecycleSupport
    implements NodeAccess
{
  private final Provider<KeyStoreManager> keyStoreProvider;

  private Certificate certificate;

  private String fingerprint;

  private String id;

  private Map<String, String> memberAliases = Collections.emptyMap();

  @Inject
  public LocalNodeAccess(@Named(KeyStoreManagerImpl.NAME) final Provider<KeyStoreManager> keyStoreProvider) {
    this.keyStoreProvider = checkNotNull(keyStoreProvider);
  }

  @Override
  protected void doStart() throws Exception {
    KeyStoreManager keyStoreManager = keyStoreProvider.get();

    // Generate identity key-pair if not already created
    if (!keyStoreManager.isKeyPairInitialized()) {
      log.info("Generating certificate");

      // For now give something unique to the cert for additional identification purposes
      UUID cn = UUID.randomUUID();
      keyStoreManager.generateAndStoreKeyPair(
          cn.toString(),
          "Trust Repository",
          "TianheCloud",
          "Silver Spring",
          "MD",
          "US");
    }

    certificate = keyStoreManager.getCertificate();
    log.trace("Certificate:\n{}", certificate);

    fingerprint = CertificateUtil.calculateFingerprint(certificate);
    log.debug("Fingerprint: {}", fingerprint);

    id = NodeIdEncoding.nodeIdForCertificate(certificate);
    log.info("ID: {}", id);

    memberAliases = ImmutableMap.of(id, id);
  }

  @Override
  protected void doStop() throws Exception {
    certificate = null;
    fingerprint = null;
    id = null;
  }

  @Override
  @Guarded(by = STARTED)
  public Certificate getCertificate() {
    return certificate;
  }

  @Override
  @Guarded(by = STARTED)
  public String getFingerprint() {
    return fingerprint;
  }

  @Override
  @Guarded(by = STARTED)
  public String getId() {
    return id;
  }

  @Override
  public boolean isClustered() {
    return false;
  }

  @Override
  public Set<String> getMemberIds() {
    return memberAliases.keySet();
  }

  @Override
  public boolean isOldestNode() {
    return true;
  }

  @Override
  public Map<String, String> getMemberAliases() {
    return memberAliases;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "id='" + id + '\'' +
        '}';
  }
}
