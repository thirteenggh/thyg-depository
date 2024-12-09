package org.sonatype.nexus.repository.golang.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.golang.GolangFormat;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * Go format security resource.
 *
 * @since 3.17
 */
@Named
@Singleton
public class GolangFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public GolangFormatSecurityContributor(@Named(GolangFormat.NAME) final Format format) {
    super(format);
  }
}
