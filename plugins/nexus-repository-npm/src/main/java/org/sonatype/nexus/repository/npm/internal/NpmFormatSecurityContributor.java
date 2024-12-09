package org.sonatype.nexus.repository.npm.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * NPM format security contributor.
 *
 * @since 3.0
 */
@Named
@Singleton
public class NpmFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public NpmFormatSecurityContributor(@Named(NpmFormat.NAME) final Format format) {
    super(format);
  }
}
