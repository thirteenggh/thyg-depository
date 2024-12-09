package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Raw {@link Repository} {@link Configuration}
 */
@CompileStatic
trait RawRepoRecipes
    extends ConfigurationRecipes
{

  @Nonnull
  Repository createRawHosted(final String name,
                             final String writePolicy = "ALLOW",
                             final boolean strictContentTypeValidation = true)
  {
    createRepository(createHosted(name, 'raw-hosted', writePolicy, strictContentTypeValidation))
  }

  @Nonnull
  Repository createRawProxy(final String name,
                            final String remoteUrl)
  {
    createRepository(createProxy(name, 'raw-proxy', remoteUrl))
  }

  @Nonnull
  Repository createRawGroup(final String name,
                            final String... members)
  {
    createRepository(createGroup(name, 'raw-group', members))
  }

  abstract Repository createRepository(final Configuration configuration)

}
