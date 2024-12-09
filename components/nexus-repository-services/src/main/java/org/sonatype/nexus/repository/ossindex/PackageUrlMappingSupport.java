package org.sonatype.nexus.repository.ossindex;

import java.util.Optional;

import org.sonatype.goodies.packageurl.PackageUrl;
import org.sonatype.goodies.packageurl.PackageUrlBuilder;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.of;

/**
 * Support for {@link PackageUrlMapping}s.
 *
 * @since 3.26
 */
public abstract class PackageUrlMappingSupport
    implements PackageUrlMapping
{
  private final String format;

  protected PackageUrlMappingSupport(final String format) {
    this.format = checkNotNull(format);
  }

  @Override
  public Optional<PackageUrl> buildPackageUrl(final String namespace, final String name, final String version) {
    return of(new PackageUrlBuilder().type(format).namespace(namespace).name(name).version(version).build());
  }
}
