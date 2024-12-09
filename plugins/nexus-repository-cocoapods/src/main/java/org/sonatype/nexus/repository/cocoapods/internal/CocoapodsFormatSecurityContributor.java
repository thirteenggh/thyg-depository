package org.sonatype.nexus.repository.cocoapods.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * Cocoapods format security resource
 *
 * @since 3.19
 */
@Named
@Singleton
public class CocoapodsFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public CocoapodsFormatSecurityContributor(@Named(CocoapodsFormat.NAME) final Format format) {
    super(format);
  }
}
