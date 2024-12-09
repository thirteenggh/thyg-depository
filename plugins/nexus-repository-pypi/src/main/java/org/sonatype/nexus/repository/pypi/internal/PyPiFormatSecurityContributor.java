package org.sonatype.nexus.repository.pypi.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * PyPI format security contributor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class PyPiFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public PyPiFormatSecurityContributor(@Named(PyPiFormat.NAME) final Format format) {
    super(format);
  }
}
