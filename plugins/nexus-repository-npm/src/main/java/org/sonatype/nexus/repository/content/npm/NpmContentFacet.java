package org.sonatype.nexus.repository.content.npm;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.npm.internal.NpmPackageId;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * Provides persistent content for the 'npm' format.
 *
 * @since 3.28
 */
@Facet.Exposed
public interface NpmContentFacet
    extends ContentFacet
{
  /**
   * Delete the package root for the given packageId
   */
  boolean delete(NpmPackageId packageId) throws IOException;

  /**
   * Delete the tarball associated with the given packageId and version.
   */
  boolean delete(NpmPackageId packageId, String version) throws IOException;

  /**
   * Retrieve the package root for the given packageId
   */
  Optional<Content> get(NpmPackageId packageId) throws IOException;

  /**
   * Retrieve the tarball associated with the given packageId and version.
   */
  Optional<Content> get(NpmPackageId packageId, String version) throws IOException;

  /**
   * Set the contents of the search index
   */
  Content putSearchIndex(Content content);

  /**
   * Set the repository root content
   */
  Content putRepositoryRoot(Content content);

  /**
   * Upload the package root for the given packageId
   */
  FluentAsset put(NpmPackageId packageId, Payload content) throws IOException;

  /**
   * Upload the package root for the given packageId
   */
  FluentAsset put(NpmPackageId packageId, TempBlob tempBlob) throws IOException;

  /**
   * Upload the tarball associated with the given packageId and version.
   */
  Content put(NpmPackageId packageId, String version, Map<String, Object> npmAttributes, Payload content) throws IOException;


  /**
   * Upload the tarball associated with the given packageId, tarball name and version.
   */
  Content put(
      NpmPackageId packageId,
      String tarballName,
      String version,
      Map<String, Object> npmAttributes,
      Payload content) throws IOException;

  /**
   * Upload the tarball associated with the given packageId and version.
   */
  Content put(NpmPackageId packageId, String version, Map<String, Object> npmAttributes, TempBlob tempBlob) throws IOException;


  static String metadataPath(final NpmPackageId packageId) {
    return '/' + packageId.id();
  }

  static String tarballPath(final NpmPackageId packageId, final String version) {
    return '/' + packageId.id() + "/-/" + packageId.name() + '-' + version + ".tgz";
  }

  static String tarballPath(final String id, final String tarballName) {
    return '/' + id + "/-/" + tarballName;
  }
}
