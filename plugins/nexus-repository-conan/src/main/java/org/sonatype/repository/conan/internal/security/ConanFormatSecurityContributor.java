package org.sonatype.repository.conan.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;
import org.sonatype.repository.conan.internal.ConanFormat;

/**
 * Conan format security resource.
 *
 * @since 3.28
 */
@Named
@Singleton
public class ConanFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public ConanFormatSecurityContributor(@Named(ConanFormat.NAME) final Format format) {
    super(format);
  }
}
