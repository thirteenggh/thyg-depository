package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Apt {@link Repository} {@link Configuration}
 */
@CompileStatic
trait AptRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createAptHosted(final String name,
                             final String distribution,
                             final String keypair,
                             final String writePolicy = "ALLOW")
  {
    def configuration = createHosted(name, 'apt-hosted', writePolicy)
    configuration.attributes.apt = [
        'distribution': distribution] as Map
    configuration.attributes.aptSigning = ['keypair': keypair] as Map
    createRepository(configuration)
  }

  @Nonnull
  Repository createAptProxy(final String name, final String remoteUrl, final String distribution, Boolean flat = false) {
    def configuration = createProxy(name, 'apt-proxy', remoteUrl)
    configuration.attributes.apt = ['distribution': distribution, 'flat': flat] as Map
    createRepository(configuration)
  }
  abstract Repository createRepository(final Configuration configuration)
}
