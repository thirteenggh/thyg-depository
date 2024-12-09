package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy

import groovy.transform.CompileStatic

/**
 * Factory for PyPi {@link Repository} {@link Configuration}
 */
@CompileStatic
trait PyPiRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createPyPiProxy(final String name, final String remoteUrl) {
    createRepository(createProxy(name, 'pypi-proxy', remoteUrl, false))
  }

  @Nonnull
  Repository createPyPiHosted(final String name, final WritePolicy writePolicy = WritePolicy.ALLOW) {
    createRepository(createHosted(name, 'pypi-hosted', writePolicy, false))
  }

  @Nonnull
  Repository createPyPiGroup(final String name, final String... members) {
    createRepository(createGroup(name, 'pypi-group', members))
  }

  abstract Repository createRepository(final Configuration configuration)
}
