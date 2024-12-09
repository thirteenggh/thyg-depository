package org.sonatype.nexus.crypto;

/**
 * Factory for {@link PbeCipher}s, for password based encryption (PBE).
 *
 * To be used on smaller payloads like user passwords or smaller messages, due to use of byte arrays for payload.
 *
 * @since 3.0
 */
public interface PbeCipherFactory
{
  interface PbeCipher
  {
    byte[] encrypt(final byte[] bytes);

    byte[] decrypt(final byte[] bytes);
  }

  /**
   * Creates a {@link PbeCipher} with given parameters. None of the parameters may be {@code null}.
   */
  PbeCipher create(String password, String salt, String iv) throws Exception;
}
