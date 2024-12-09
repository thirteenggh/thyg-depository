package org.sonatype.repository.helm.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;
import org.sonatype.repository.helm.internal.HelmFormat;

/**
 * Helm format security resource.
 */
@Named
@Singleton
public class HelmFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public HelmFormatSecurityContributor(@Named(HelmFormat.NAME) final Format format) {
    super(format);
  }
}
