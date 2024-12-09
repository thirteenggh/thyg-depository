package org.sonatype.nexus.testsuite.testsupport.fixtures

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
  Repository createHelmHosted(final String name,
                             final String writePolicy = "ALLOW")
  {
    createRepository(createHosted(name, 'helm-hosted', writePolicy))
  }

  @Nonnull
  Repository createHelmProxy(final String name,
                            final String remoteUrl)
  {
    createRepository(createProxy(name, 'helm-proxy', remoteUrl))
  }

  abstract Repository createRepository(final Configuration configuration)

}
