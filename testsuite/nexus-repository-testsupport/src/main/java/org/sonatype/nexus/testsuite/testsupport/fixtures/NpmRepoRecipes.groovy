package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration

import groovy.transform.CompileStatic

import static org.sonatype.nexus.blobstore.api.BlobStoreManager.DEFAULT_BLOBSTORE_NAME
import static org.sonatype.nexus.repository.config.ConfigurationConstants.BLOB_STORE_NAME
import static org.sonatype.nexus.repository.config.ConfigurationConstants.STORAGE

/**
 * Factory for Npm {@link Repository} {@link Configuration}
 */
@CompileStatic
trait NpmRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createNpmHosted(final String repoName,
                             final String writePolicy = "ALLOW",
                             final String blobStoreName = null)
  {
    Configuration configuration = createHosted(repoName, 'npm-hosted', writePolicy)
    if (blobStoreName != null) {
      configuration.attributes(STORAGE).set(BLOB_STORE_NAME, blobStoreName)
    }
    return createRepository(configuration)
  }

  @Nonnull
  Repository createNpmProxy(final String name,
                            final String remoteUrl,
                            final Map<String, Object> authentication = [:]) {
    createRepository(createProxy(name, 'npm-proxy', remoteUrl, true, DEFAULT_BLOBSTORE_NAME, authentication))
  }

  @Nonnull
  Repository createNpmGroup(final String name,
                            final Repository groupWriteMember,
                            final String... members)
  {
    createRepository(createGroup(name, 'npm-group', groupWriteMember.getName(), members))
  }

  @Nonnull
  Repository createNpmGroup(final String name,
                            final String... members)
  {
    createRepository(createGroup(name, 'npm-group', members))
  }

  abstract Repository createRepository(final Configuration configuration)

}

