package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy

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
                             final WritePolicy writePolicy = WritePolicy.ALLOW,
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
