package org.sonatype.nexus.orient;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OClass;

/**
 * {@link ORID} obfuscation helper.
 *
 * @since 3.0
 */
public interface RecordIdObfuscator
{
  String encode(OClass type, ORID rid);

  ORID decode(OClass type, String encoded);
}
