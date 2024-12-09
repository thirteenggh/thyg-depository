package org.sonatype.nexus.orient;

/**
 * Performance tests for {@link HexRecordIdObfuscator}.
 */
public class HexRecordIdObfuscatorPerf
  extends RecordIdObfuscatorPerfSupport
{
  @Override
  protected RecordIdObfuscator createTestSubject() throws Exception {
    return new HexRecordIdObfuscator();
  }
}
