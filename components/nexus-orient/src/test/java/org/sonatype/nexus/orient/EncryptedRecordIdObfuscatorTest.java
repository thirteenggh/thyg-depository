package org.sonatype.nexus.orient;

import org.sonatype.nexus.crypto.internal.CryptoHelperImpl;

/**
 * Tests for {@link EncryptedRecordIdObfuscator}.
 */
public class EncryptedRecordIdObfuscatorTest
  extends RecordIdObfuscatorTestSupport
{
  @Override
  protected RecordIdObfuscator createTestSubject() throws Exception {
    return new EncryptedRecordIdObfuscator(new CryptoHelperImpl(), "password", "salt", "0123456789ABCDEF");
  }
}
