package org.sonatype.nexus.repository.r;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Query;

/**
 * @since 3.28
 */
@Facet.Exposed
public interface RRestoreFacet
    extends Facet
{
  void restore(final AssetBlob assetBlob, final String path) throws IOException;

  boolean assetExists(final String path);

  boolean componentRequired(final String name);

  Query getComponentQuery(final Map<String, String> attributes);

  Map<String, String> extractComponentAttributesFromArchive(final String filename, final InputStream is);
}
