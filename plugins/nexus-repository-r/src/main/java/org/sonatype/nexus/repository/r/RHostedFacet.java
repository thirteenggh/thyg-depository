package org.sonatype.nexus.repository.r;

import java.io.IOException;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

/**
 * Persistence for R hosted.
 *
 * @since 3.28
 */
@Facet.Exposed
public interface RHostedFacet
    extends Facet
{
  /**
   * Retrieve stored content
   *
   * @param contentPath the full path to stored content
   * @return the package content
   */
  Content getStoredContent(String contentPath);

  /**
   * Perform upload.
   *
   * @param path    the upload path
   * @param payload uploaded file content
   */
  Asset upload(String path, Payload payload) throws IOException;

  /**
   * Builds and stores PACKAGES.gz metadata for path.
   *
   * @param basePath the path to build the metadata for
   */
  Content buildAndPutPackagesGz(String basePath) throws IOException;
}
