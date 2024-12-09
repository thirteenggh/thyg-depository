package org.sonatype.nexus.repository.pypi.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.search.index.IndexSettingsContributorSupport;

/**
 * @since 3.1
 */
@Named
@Singleton
public class PyPiIndexSettingsContributor extends IndexSettingsContributorSupport
{
  @Inject
  public PyPiIndexSettingsContributor(@Named(PyPiFormat.NAME) final Format format) {
    super(format);
  }
}
