package org.sonatype.nexus.repository.apt.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * @since 3.17
 */
@Named
@Singleton
public class AptFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public AptFormatSecurityContributor(@Named(AptFormat.NAME) final Format format) {
    super(format);
  }
}
