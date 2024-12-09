package org.sonatype.nexus.repository.ossindex;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.goodies.packageurl.PackageUrl;
import org.sonatype.nexus.repository.ossindex.PackageUrlMapping;
import org.sonatype.nexus.repository.ossindex.PackageUrlService;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;

/**
 * @since 3.26
 */
@Named
@Singleton
public class PackageUrlServiceImpl
    extends ComponentSupport
    implements PackageUrlService
{
  private final Map<String, PackageUrlMapping> packageUrlMappings;

  @Inject
  public PackageUrlServiceImpl(final Map<String, PackageUrlMapping> packageUrlMappings) {
    this.packageUrlMappings = checkNotNull(packageUrlMappings);
  }

  @Override
  public Optional<PackageUrl> getPackageUrl(final String format,
                                            final String namespace,
                                            final String name,
                                            final String version)
  {
    PackageUrlMapping mapping = packageUrlMappings.get(format);
    if (mapping != null) {
      try {
        return mapping.buildPackageUrl(namespace, name, version);
      }
      catch (Exception e) {
        log.debug("Cannot determine package URL coordinates for {} namespace/name/version {}/{}/{}", format, namespace,
            name,
            version, e);
      }
    }
    return empty();
  }
}
