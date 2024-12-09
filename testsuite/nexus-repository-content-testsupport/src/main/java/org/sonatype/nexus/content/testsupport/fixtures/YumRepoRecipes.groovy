package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy

import groovy.transform.CompileStatic

/**
 * Factory for Yum {@link Repository} {@link Configuration}
 */
@CompileStatic
trait YumRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createYumProxy(final String name, final String remoteUrl) {
    createRepository(createProxy(name, 'yum-proxy', remoteUrl, false))
  }

  @Nonnull
  Repository createYumHosted(final String name, final WritePolicy writePolicy = WritePolicy.ALLOW) {
    createRepository(createHosted(name, 'yum-hosted', writePolicy, false))
  }

  @Nonnull
  Repository createYumGroup(final String name, final String... members) {
    createRepository(createGroup(name, 'yum-group', members))
  }

  abstract Repository createRepository(final Configuration configuration)
}
