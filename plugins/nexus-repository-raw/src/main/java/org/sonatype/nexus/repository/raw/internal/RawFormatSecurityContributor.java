package org.sonatype.nexus.repository.raw.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * RAW format security contributor.
 *
 * @since 3.0
 */
@Named
@Singleton
public class RawFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public RawFormatSecurityContributor(@Named(RawFormat.NAME) final Format format) {
    super(format);
  }
}
