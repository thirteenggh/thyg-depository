package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Cocoapods {@link Repository} {@link Configuration}
 */
@CompileStatic
trait CocoapodsRepoRecipes
    extends ConfigurationRecipes
{

  @Nonnull
  Repository createCocoapodsProxy(final String name, final String remoteUrl) {
    createRepository(createProxy(name, 'cocoapods-proxy', remoteUrl))
  }

  abstract Repository createRepository(final Configuration configuration)
}
