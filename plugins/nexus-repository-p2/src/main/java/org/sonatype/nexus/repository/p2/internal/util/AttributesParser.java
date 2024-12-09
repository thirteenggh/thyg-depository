package org.sonatype.nexus.repository.p2.internal.util;

import java.io.IOException;

import org.sonatype.nexus.repository.p2.internal.exception.AttributeParsingException;
import org.sonatype.nexus.repository.p2.internal.metadata.P2Attributes;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * @since 3.28
 */
public interface AttributesParser
{
  P2Attributes getAttributesFromBlob(final TempBlob tempBlob, final String extension)
      throws IOException, AttributeParsingException;
}
