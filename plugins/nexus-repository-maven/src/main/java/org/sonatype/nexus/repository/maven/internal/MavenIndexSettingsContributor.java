package org.sonatype.nexus.repository.maven.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.search.IndexSettingsContributorSupport;

/**
 * Contributor to ES index settings for Maven 2 repositories.
 *
 * @since 3.0
 */
@Named
@Singleton
public class MavenIndexSettingsContributor
    extends IndexSettingsContributorSupport
{
  @Inject
  public MavenIndexSettingsContributor(@Named(Maven2Format.NAME) final Format format) {
    super(format);
  }
}

