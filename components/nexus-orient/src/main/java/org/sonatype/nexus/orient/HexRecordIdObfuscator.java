package org.sonatype.nexus.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.io.Hex;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;

/**
 * HEX-encoding {@link RecordIdObfuscator}.
 *
 * @since 3.0
 */
@Named("hex")
@Singleton
public class HexRecordIdObfuscator
  extends RecordIdObfuscatorSupport
{
  @Override
  protected String doEncode(final OClass type, final ORID rid) throws Exception {
    return Hex.encode(rid.toStream());
  }

  @Override
  protected ORID doDecode(final OClass type, final String encoded) throws Exception {
    byte[] decoded = Hex.decode(encoded);
    return new ORecordId().fromStream(decoded);
  }
}
