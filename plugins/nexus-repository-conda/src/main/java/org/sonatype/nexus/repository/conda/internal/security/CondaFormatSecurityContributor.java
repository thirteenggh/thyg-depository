package org.sonatype.nexus.repository.conda.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.conda.internal.CondaFormat;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * Conda format security resource.
 *
 * @since 3.19
 */
@Named
@Singleton
public class CondaFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public CondaFormatSecurityContributor(@Named(CondaFormat.NAME) final Format format) {
    super(format);
  }
}
