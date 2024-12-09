package org.sonatype.nexus.repository.ossindex;

import java.util.Optional;

import org.sonatype.goodies.packageurl.PackageUrl;

/**
 * {@link PackageUrl} lookup service.
 *
 * @since 3.26
 */
public interface PackageUrlService
{
  /**
   * Returns {@link PackageUrl} for the given component, if it exists.
   */
  Optional<PackageUrl> getPackageUrl(String format, String namespace, String name, String version);
}
