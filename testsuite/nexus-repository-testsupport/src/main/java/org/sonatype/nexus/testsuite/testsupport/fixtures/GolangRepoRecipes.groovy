package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Go {@link Repository} {@link Configuration}
 */
@CompileStatic
trait GolangRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createGolangProxy(final String name,
                               final String remoteUrl)
  {
    createRepository(createProxy(name, 'go-proxy', remoteUrl))
  }

  @Nonnull
  Repository createGolangHosted(final String name)
  {
    createRepository(createHosted(name, 'go-hosted'))
  }

  @Nonnull
  Repository createGolangGroup(final String name, String ...members) {
    createRepository(createGroup(name, 'go-group', members))
  }

  abstract Repository createRepository(final Configuration configuration)
}
