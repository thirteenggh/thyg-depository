package com.sonatype.nexus.ssl.plugin.internal.keystore.orient;

import org.sonatype.nexus.common.entity.AbstractEntity;

/**
 * The persisted data of a named {@link java.security.KeyStore}.
 * 
 * @since 3.1
 */
public class OrientKeyStoreData
    extends AbstractEntity
{
  private String name;

  private byte[] bytes;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public byte[] getBytes() {
    return bytes; // NOSONAR
  }

  public void setBytes(final byte[] bytes) { // NOSONAR
    this.bytes = bytes; // NOSONAR
  }
}
