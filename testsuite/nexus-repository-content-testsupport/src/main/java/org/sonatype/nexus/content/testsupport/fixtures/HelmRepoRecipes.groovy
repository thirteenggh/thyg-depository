package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Helm {@link Repository} {@link Configuration}
 */
@CompileStatic
trait HelmRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createHelmProxy(final String name,
                             final String remoteUrl)
  {
    createRepository(createProxy(name, 'helm-proxy', remoteUrl))
  }

  @Nonnull
  Repository createHelmHosted(final String name)
  {
    createRepository(createHosted(name, 'helm-hosted'))
  }

  abstract Repository createRepository(final Configuration configuration)
}
