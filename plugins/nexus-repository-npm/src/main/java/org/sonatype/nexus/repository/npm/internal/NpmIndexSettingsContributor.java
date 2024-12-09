package org.sonatype.nexus.repository.npm.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.search.IndexSettingsContributorSupport;

/**
 * @since 3.7
 */
@Named
@Singleton
public class NpmIndexSettingsContributor extends IndexSettingsContributorSupport
{
  @Inject
  public NpmIndexSettingsContributor(@Named(NpmFormat.NAME) final Format format) {
    super(format);
  }
}
