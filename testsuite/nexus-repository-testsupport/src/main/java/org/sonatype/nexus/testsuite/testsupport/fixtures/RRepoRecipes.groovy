package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy

import groovy.transform.CompileStatic;

/**
 * Factory for R {@link Repository} {@link Configuration}
 */
@CompileStatic
trait RRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createRProxy(final String name,
                          final String remoteUrl)
  {
    createRepository(createProxy(name, 'r-proxy', remoteUrl))
  }

  @Nonnull
  Repository createRHosted(final String name) {
    createRepository(createHosted(name, 'r-hosted'))
  }

  @Nonnull
  Repository createRHosted(final String name, final WritePolicy writePolicy) {
    createRepository(createHosted(name, 'r-hosted', writePolicy.toString()))
  }

  @Nonnull
  Repository createRGroup(final String name, final String... members) {
    createRepository(createGroup(name, 'r-group', members))
  }

  abstract Repository createRepository(final Configuration configuration)
}
