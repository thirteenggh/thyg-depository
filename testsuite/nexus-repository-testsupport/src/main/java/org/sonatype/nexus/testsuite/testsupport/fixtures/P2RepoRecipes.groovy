package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.testsuite.testsupport.fixtures.ConfigurationRecipes

import groovy.transform.CompileStatic

/**
 * Factory for p2 {@link Repository} {@link Configuration}
 */
@CompileStatic
trait P2RepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createP2Proxy(final String name, final String remoteUrl)
  {
    createRepository(createProxy(name, 'p2-proxy', remoteUrl))
  }

  abstract Repository createRepository(final Configuration configuration)
}
