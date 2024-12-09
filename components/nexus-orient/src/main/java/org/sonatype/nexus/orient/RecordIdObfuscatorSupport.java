package org.sonatype.nexus.orient;

import org.sonatype.goodies.common.ComponentSupport;

import com.google.common.base.Throwables;
import com.google.common.primitives.Ints;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OClass;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Support for {@link RecordIdObfuscator} implementations.
 *
 * @since 3.0
 */
public abstract class RecordIdObfuscatorSupport
    extends ComponentSupport
    implements RecordIdObfuscator
{
  /**
   * @see #doEncode(OClass, ORID)
   */
  @Override
  public String encode(final OClass type, final ORID rid) {
    checkNotNull(type);
    checkNotNull(rid);

    log.trace("Encoding: {}->{}", type, rid);
    try {
      return doEncode(type, rid);
    }
    catch (Exception e) {
      log.error("Failed to encode: {}->{}", type, rid);
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }
  }

  /**
   * @see #encode(OClass, ORID)
   */
  protected abstract String doEncode(final OClass type, final ORID rid) throws Exception;

  /**
   * @see #doDecode(OClass, String)
   */
  @Override
  public ORID decode(final OClass type, final String encoded) {
    checkNotNull(type);
    checkNotNull(encoded);

    log.trace("Decoding: {}->{}", type, encoded);
    ORID rid;
    try {
      rid = doDecode(type, encoded);
    }
    catch (Exception e) {
      log.error("Failed to decode: {}->{}", type, encoded);
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }

    // ensure rid points to the right type
    checkArgument(Ints.contains(type.getClusterIds(), rid.getClusterId()),
        "Invalid RID '%s' for class: %s", rid, type);

    return rid;
  }

  /**
   * @see #decode(OClass, String)
   */
  protected abstract ORID doDecode(final OClass type, final String encoded) throws Exception;
}
