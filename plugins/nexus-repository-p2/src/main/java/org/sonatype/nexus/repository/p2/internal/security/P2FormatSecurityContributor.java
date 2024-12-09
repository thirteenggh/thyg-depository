package org.sonatype.nexus.repository.p2.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.p2.internal.P2Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * P2 format security resource.
 */
@Named
@Singleton
public class P2FormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public P2FormatSecurityContributor(@Named(P2Format.NAME) final Format format) {
    super(format);
  }
}
