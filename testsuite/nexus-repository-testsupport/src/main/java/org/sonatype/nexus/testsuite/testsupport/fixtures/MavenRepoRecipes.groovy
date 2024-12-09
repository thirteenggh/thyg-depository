package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

/**
 * Factory for Maven {@link Repository} {@link Configuration}
 */
@CompileStatic
trait MavenRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createMavenHosted(final String name,
                               final String versionPolicy = "RELEASE",
                               final String writePolicy = "ALLOW_ONCE",
                               final String layoutPolicy = "STRICT",
                               final String blobStoreName = BlobStoreManager.DEFAULT_BLOBSTORE_NAME)
  {
    Configuration configuration =
        createHosted(name, 'maven2-hosted', writePolicy, true, blobStoreName)
    configuration.attributes.maven = configureMaven(versionPolicy, layoutPolicy)
    createRepository(configuration)
  }

  @Nonnull
  Repository createMavenProxy(final String name,
                              final String remoteUrl,
                              final String versionPolicy = "RELEASE",
                              final String layoutPolicy = "STRICT")
  {
    Configuration configuration = createProxy(name, 'maven2-proxy', remoteUrl)
    configuration.attributes.maven = configureMaven(versionPolicy, layoutPolicy)
    createRepository(configuration)
  }

  @Nonnull
  Repository createMavenGroup(final String name,
                              final String... members)
  {
    Configuration configuration = createGroup(name, 'maven2-group', members)
    configuration.attributes.maven = configureMaven()
    createRepository(configuration)
  }

  private Map configureMaven(final String versionPolicy = "MIXED",
                             final String layoutPolicy = "STRICT") {
    [versionPolicy: versionPolicy as String, layoutPolicy: layoutPolicy as String] as Map
  }

  abstract Repository createRepository(final Configuration configuration)
}
