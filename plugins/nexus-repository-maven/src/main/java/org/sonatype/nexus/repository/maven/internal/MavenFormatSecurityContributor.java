package org.sonatype.nexus.repository.maven.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * Maven 2 format security contributor.
 *
 * @since 3.0
 */
@Named
@Singleton
public class MavenFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public MavenFormatSecurityContributor(@Named(Maven2Format.NAME) final Format format) {
    super(format);
  }
}
