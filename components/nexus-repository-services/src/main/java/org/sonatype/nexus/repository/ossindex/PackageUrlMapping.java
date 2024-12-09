package org.sonatype.nexus.repository.ossindex;

import java.util.Optional;

import javax.annotation.Nullable;

import org.sonatype.goodies.packageurl.PackageUrl;

/**
 * Represents a format-specific mapping of components to {@link PackageUrl}s.
 *
 * All formats should implement this to get vulnerability information
 * @see https://ossindex.sonatype.org/ecosystems
 *
 * @since 3.26
 */
public interface PackageUrlMapping
{
  /**
   * Returns {@link PackageUrl} for the given component coordinates.
   */
  Optional<PackageUrl> buildPackageUrl(@Nullable String namespace, String name, @Nullable String version);
}
