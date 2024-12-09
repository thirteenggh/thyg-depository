package org.sonatype.nexus.repository.search.index;

import java.net.URL;

import javax.annotation.Nullable;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;

import com.google.common.io.Resources;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Support for {@link IndexSettingsContributor} implementations.
 *
 * @since 3.0
 */
public class IndexSettingsContributorSupport
    extends ComponentSupport
    implements IndexSettingsContributor
{
  private final Format format;

  public IndexSettingsContributorSupport(final Format format) {
    this.format = checkNotNull(format);
  }

  @Override
  @Nullable
  public URL getIndexSettings(final Repository repository) {
    checkNotNull(repository);
    if (format.equals(repository.getFormat())) {
      return Resources.getResource(getClass(), MAPPING_JSON);
    }
    return null;
  }
}
