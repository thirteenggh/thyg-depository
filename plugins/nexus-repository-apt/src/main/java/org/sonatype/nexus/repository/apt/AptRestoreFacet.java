package org.sonatype.nexus.repository.apt;

import java.io.IOException;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Query;
import org.sonatype.nexus.repository.view.Content;

/**
 * @since 3.17
 */
@Facet.Exposed
public interface AptRestoreFacet extends Facet
{
  Content restore(final AssetBlob assetBlob, final String path) throws IOException;

  boolean assetExists(final String path);

  Query getComponentQuery(final Blob blob) throws IOException;

  boolean componentRequired(final String name);
}
