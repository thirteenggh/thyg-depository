package org.sonatype.nexus.repository.search;

import org.sonatype.nexus.repository.Format;

/**
 * @deprecated Use {@link org.sonatype.nexus.repository.search.index.IndexSettingsContributorSupport} instead
 */
@Deprecated
public abstract class IndexSettingsContributorSupport
    extends org.sonatype.nexus.repository.search.index.IndexSettingsContributorSupport
{
  protected IndexSettingsContributorSupport(final Format format) {
    super(format);
  }
}
