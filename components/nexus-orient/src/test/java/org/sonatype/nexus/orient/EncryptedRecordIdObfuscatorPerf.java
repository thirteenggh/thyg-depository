package org.sonatype.nexus.orient;

import org.sonatype.nexus.crypto.internal.CryptoHelperImpl;

/**
 * Performance tests for {@link EncryptedRecordIdObfuscator}.
 */
public class EncryptedRecordIdObfuscatorPerf
  extends RecordIdObfuscatorPerfSupport
{
  @Override
  protected RecordIdObfuscator createTestSubject() throws Exception {
    return new EncryptedRecordIdObfuscator(new CryptoHelperImpl(), "changeme", "changeme", "0123456789ABCDEF");
  }
}
