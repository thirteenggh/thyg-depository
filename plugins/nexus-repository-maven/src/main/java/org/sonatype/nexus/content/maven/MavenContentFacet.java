package org.sonatype.nexus.content.maven;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

/**
 * Provides persistence operations for Maven.
 *
 * @since 3.25
 */
@Facet.Exposed
public interface MavenContentFacet
    extends ContentFacet, MavenFacet
{
  /**
   * Get a maven asset
   *
   * @param mavenPath Path of asset to get
   */
  Optional<Content> get(MavenPath mavenPath) throws IOException;

  /**
   * Put a maven asset
   *
   * @param path    The path of the asset to put
   * @param content The content to put
   */
  Content put(MavenPath path, Payload content) throws IOException;

  /**
   * Delete a maven asset
   *
   * @param path The path of the asset to delete.
   * @return True if asset was deleted or false if not.
   */
  boolean delete(MavenPath path) throws IOException;

  /**
   * Deletes the assets at the specified paths.
   *
   * @since 3.29
   */
  boolean delete(final List<String> paths);

  /**
   * Delete the specified maven asset and its hashes
   *
   * @param path The path of the asset to delete
   * @return The paths of the assets deleted
   */
  Set<String> deleteWithHashes(MavenPath path) throws IOException;

  /**
   * Update component attributes when {@code path} corresponds to a Maven POM
   *
   * @param path for which the corresponding component attributes may be updated
   */
  void maybeUpdateComponentAttributes(MavenPath path) throws IOException;
}
