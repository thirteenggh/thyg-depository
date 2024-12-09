package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull

import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy

import groovy.transform.CompileStatic

@CompileStatic
trait MavenRepoRecipes
    extends ConfigurationRecipes
{
  @Nonnull
  Repository createMavenHosted(final String name,
                               final String versionPolicy = "RELEASE",
                               final WritePolicy writePolicy = WritePolicy.ALLOW_ONCE,
                               final String layoutPolicy = "STRICT",
                               final String blobStoreName = BlobStoreManager.DEFAULT_BLOBSTORE_NAME)
  {
    Configuration configuration = createHosted(name, 'maven2-hosted', writePolicy, true, blobStoreName)
    configuration.attributes.maven = [
        versionPolicy: versionPolicy as String,
        layoutPolicy : layoutPolicy as String
    ] as Map
    createRepository(configuration)
  }

  abstract Repository createRepository(final Configuration configuration)

}
