package org.sonatype.nexus.common.node;

import java.security.cert.Certificate;
import java.util.Map;
import java.util.Set;

import org.sonatype.goodies.lifecycle.Lifecycle;

/**
 * Provides access to node details.
 * 
 * @since 3.1
 */
public interface NodeAccess
    extends Lifecycle
{
  /**
   * Returns the local-node certificate.
   */
  Certificate getCertificate();

  /**
   * Returns the local-node fingerprint.
   *
   * This is the SHA1 of the certificate.
   */
  String getFingerprint();

  /**
   * Returns the local-node identifier.
   * <p/>
   * ID is based on certificate fingerprint digest encoded with "-" every 8 characters for better human readability while
   * remaining terse:
   * <p/>
   * <pre>05F4743F-A7565846-43FDF9D0-577BE4FB-079289C6</pre>
   */
  String getId();

  /**
   * Returns true if the node is clustered.
   */
  boolean isClustered();

  /**
   * Returns identifiers of clustered nodes.
   */
  Set<String> getMemberIds();

  /**
   * Is this the oldest node in the cluster?
   *
   * @since 3.3
   */
  boolean isOldestNode();

  /**
   * Returns a {@link Map} of {@link #getId()} keys to friendly node name alias values.
   * Values in the {@link Map} should never be null; if no alias is set for a member, the
   * value in the map for the {@link #getId()} must also be {@link #getId()}.
   *
   * @since 3.6.1
   */
  Map<String, String> getMemberAliases();
}
