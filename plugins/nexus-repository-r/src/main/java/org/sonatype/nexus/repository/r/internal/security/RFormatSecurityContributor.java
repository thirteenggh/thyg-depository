package org.sonatype.nexus.repository.r.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.r.internal.RFormat;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * R format security resource.
 *
 * @since 3.28
 */
@Named
@Singleton
public class RFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public RFormatSecurityContributor(@Named(RFormat.NAME) final Format format) {
    super(format);
  }
}
