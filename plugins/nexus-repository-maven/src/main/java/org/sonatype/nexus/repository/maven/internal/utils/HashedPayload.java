package org.sonatype.nexus.repository.maven.internal.utils;

import java.util.Map;

import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.view.Payload;

import com.google.common.hash.HashCode;

/**
 * Encapsulates a Payload and hashed representations of that payload
 *
 * @since 3.25
 */
public class HashedPayload
{
  private final Payload payload;

  private final Map<HashAlgorithm, HashCode> hashCodes;

  HashedPayload(final Payload payload, final Map<HashAlgorithm, HashCode> hashCodes) {
    this.payload = payload;
    this.hashCodes = hashCodes;
  }

  public Payload getPayload() {
    return payload;
  }

  public Map<HashAlgorithm, HashCode> getHashCodes() {
    return hashCodes;
  }
}
