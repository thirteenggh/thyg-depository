package com.sonatype.nexus.ssl.plugin.internal.keystore;

import java.security.KeyStore;

import org.sonatype.nexus.common.entity.EntityVersion;

/**
 * Event sent out when {@link KeyStore} data changes.
 *
 * @since 3.1
 */
public interface KeyStoreDataEvent
{
  boolean isLocal();

  String getRemoteNodeId();

  EntityVersion getVersion();

  String getKeyStoreName();
}
