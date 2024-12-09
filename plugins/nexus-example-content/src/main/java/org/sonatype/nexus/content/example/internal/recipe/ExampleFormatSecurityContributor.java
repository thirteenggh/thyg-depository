package org.sonatype.nexus.content.example.internal.recipe;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.security.RepositoryFormatSecurityContributor;

/**
 * Example format security contributor.
 *
 * @since 3.24
 */
@Named
@Singleton
public class ExampleFormatSecurityContributor
    extends RepositoryFormatSecurityContributor
{
  @Inject
  public ExampleFormatSecurityContributor(@Named(ExampleFormat.NAME) final Format format) {
    super(format);
  }
}
