package org.sonatype.nexus.orient;

/**
 * Tests for {@link HexRecordIdObfuscator}.
 */
public class HexRecordIdObfuscatorTest
  extends RecordIdObfuscatorTestSupport
{
  @Override
  protected RecordIdObfuscator createTestSubject() throws Exception {
    return new HexRecordIdObfuscator();
  }
}
