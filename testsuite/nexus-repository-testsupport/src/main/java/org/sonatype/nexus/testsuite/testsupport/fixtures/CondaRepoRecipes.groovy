package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Conda {@link Repository} {@link Configuration}
 * @since 3.19
 */
@CompileStatic
trait CondaRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createCondaProxy(final String name, final String remoteUrl) {
    def configuration = createProxy(name, 'conda-proxy', remoteUrl)
    createRepository(configuration)
  }

  abstract Repository createRepository(final Configuration configuration)
}
